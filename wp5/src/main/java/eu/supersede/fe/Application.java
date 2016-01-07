package eu.supersede.fe;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan
@EnableGlobalMethodSecurity( securedEnabled = true )
@EnableScheduling
@EnableRedisHttpSession
public class Application extends SpringBootServletInitializer {
	
	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver  slr = new AcceptHeaderLocaleResolver ();
	    return slr;
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}