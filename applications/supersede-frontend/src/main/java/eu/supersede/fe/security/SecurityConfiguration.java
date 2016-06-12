package eu.supersede.fe.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Profile;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:wp5.properties")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("#{'${web.security.permit.urls}'.split(',')}") 
	private String[] PERMIT_URLS;
	
	private static final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UsersJpa users;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(dbUserDetailsService()).passwordEncoder(bcryptEncoder);
	}
	
	@Bean
	UserDetailsService dbUserDetailsService() {
		return new UserDetailsService() {

			@Override
			@Transactional
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				eu.supersede.fe.model.User user = users.findByEmail(email);
				
				try
				{
				if (user != null) {
					log.debug("User found");
					//get authorities from profiles
					List<Profile> profiles = user.getProfiles();
					String[] authorities = new String[profiles.size()];
					for(int i = 0; i < profiles.size(); i++)
					{
						authorities[i] = "ROLE_" + profiles.get(i).getName();
					}
					
					log.debug("User has " + authorities.length + " authorities");
					
					List<GrantedAuthority> permissions = AuthorityUtils.createAuthorityList(authorities);
					return new DatabaseUser(user.getUserId(), user.getName(), user.getEmail(), user.getPassword(), true, true, true, true, permissions, user.getLocale());
				}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					log.error(ex.getMessage());
				}
				throw new UsernameNotFoundException("could not find the user '" + email + "'");
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
				.antMatchers(PERMIT_URLS).permitAll()
				.anyRequest().authenticated().and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}