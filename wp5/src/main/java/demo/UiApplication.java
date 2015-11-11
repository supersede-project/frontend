package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan
@EnableGlobalMethodSecurity( securedEnabled = true )
@EnableScheduling
public class UiApplication extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(UiApplication.class);
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UiApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

}