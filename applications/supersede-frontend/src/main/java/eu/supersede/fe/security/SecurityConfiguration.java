/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import eu.supersede.fe.integration.ProxyWrapper;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.User;
import eu.supersede.integration.api.security.types.AuthorizationToken;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:wp5.properties")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("#{'${web.security.permit.urls}'.split(',')}")
    private String[] PERMIT_URLS;

    @Value("${security.configuration.IFAuthManager}")
    private Boolean AUTH_MANAGER_ENABLED;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        // auth.userDetailsService(dbUserDetailsService()).passwordEncoder(bcryptEncoder);
        auth.authenticationProvider(customAuthenticationProvider());
    }

    @Autowired
    ProxyWrapper proxy;

    @Autowired
    UsersJpa users;

    @Bean
    AuthenticationProvider customAuthenticationProvider()
    {
        return new AuthenticationProvider()
        {
            private final Logger log = LoggerFactory.getLogger(this.getClass());

            @Override
            @Transactional
            public Authentication authenticate(Authentication auth) throws AuthenticationException
            {
                String username = (String) auth.getPrincipal();
                String password = (String) auth.getCredentials();

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes();
                HttpServletRequest req = attr.getRequest();
                String tenantId = req.getHeader("TenantId");

                if (tenantId == null)
                {
                    log.error("Tenant provided");
                    throw new BadCredentialsException("Invalid login request: missing tenant");
                }

                AuthorizationToken token = getAuthToken(username, password, tenantId);
                User user = users.findByUsername(username);

                if (user == null)
                {
                    log.error("Username not found in Database");
                    throw new BadCredentialsException("Invalid login request: user " + username + " not found");
                }

                // get authorities from profiles
                List<Profile> profiles = user.getProfiles();
                String[] authorities = new String[profiles.size()];

                for (int i = 0; i < profiles.size(); i++)
                {
                    authorities[i] = "ROLE_" + profiles.get(i).getName();
                }

                log.debug("User has " + authorities.length + " authorities");

                List<GrantedAuthority> permissions = AuthorityUtils.createAuthorityList(authorities);

                DatabaseUser dbUser = new DatabaseUser(user.getUserId(), user.getFirstName() + " " + user.getLastName(),
                        user.getEmail(), password, token, true, true, true, true, permissions, user.getLocale());

                return new UsernamePasswordAuthenticationToken(dbUser, password, permissions);// AUTHORITIES
            }

            private AuthorizationToken getAuthToken(String username, String password, String tenantId)
            {
                AuthorizationToken token = null;

                if (AUTH_MANAGER_ENABLED)
                {
                    try
                    {
                        token = proxy.getIFAuthenticationManager(tenantId).getAuthorizationToken(username, password,
                                tenantId);
                    }
                    catch (HttpClientErrorException e)
                    {
                        log.error("Invalid username and password.");
                    }
                    catch (NullPointerException e1)
                    {
                        log.error("Authorization token is null, check your if.properties file in the conf/ folder");
                    }
                    catch (Exception e2)
                    {
                        e2.printStackTrace();
                    }

                    if (token == null || token.getAccessToken() == null)
                    {
                        log.error("Supersede integration token is null");
                        throw new BadCredentialsException(
                                "Invalid login request: authentication manager token is null");
                    }
                }
                else
                {
                    log.warn("IF Authentication Manager disable, user token is NULL");
                }

                return token;
            }

            @Override
            @SuppressWarnings("rawtypes")
            public boolean supports(Class authentication)
            {
                return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.httpBasic().and().authorizeRequests().antMatchers(PERMIT_URLS).permitAll().anyRequest().authenticated();

        // TODO: investigate exception thrown by the doFilter method.
        // Temporary fix: do not apply the filter.
        // .and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
        // .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

    private Filter csrfHeaderFilter()
    {
        return new OncePerRequestFilter()
        {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                    FilterChain filterChain) throws ServletException, IOException
            {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

                if (csrf != null)
                {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();

                    if (cookie == null || token != null && !token.equals(cookie.getValue()))
                    {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository()
    {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}