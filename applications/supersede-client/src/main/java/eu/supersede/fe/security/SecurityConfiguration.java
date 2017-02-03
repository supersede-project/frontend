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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:wp5_application.properties")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private static boolean csrf_error = false;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${application.unsecured.urls:}")
    private String PERMIT_URLS;

    private static CsrfRequestMatcher csrfRequestMatcher;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        String[] permitUrls;

        if (PERMIT_URLS.equals(""))
        {
            permitUrls = new String[0];
        }
        else
        {
            permitUrls = PERMIT_URLS.split(",");

            for (int i = 0; i < permitUrls.length; i++)
            {
                permitUrls[i] = permitUrls[i].trim();
            }
        }

        if (csrfRequestMatcher == null)
        {
            csrfRequestMatcher = new CsrfRequestMatcher(permitUrls);
        }

        http.httpBasic().disable();
        http.authorizeRequests().antMatchers(permitUrls).permitAll().anyRequest().authenticated().and().csrf()
                .requireCsrfProtectionMatcher(csrfRequestMatcher).and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
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

                try
                {
                    filterChain.doFilter(request, response);
                }
                catch (IOException e)
                {
                    if (!csrf_error)
                    {
                        log.warn("Unable to apply the CSRF filter. This message will not be displayed again");
                    }
                    else
                    {
                        csrf_error = true;
                    }
                }
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository()
    {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public CookieSerializer cookieSerializer()
    {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookiePath("/");
        return serializer;
    }
}