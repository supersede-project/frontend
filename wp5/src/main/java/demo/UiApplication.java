package demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan
@EnableGlobalMethodSecurity( securedEnabled = true )
@EnableScheduling
public class UiApplication {

	private static final Logger log = LoggerFactory.getLogger(UiApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
		
		log.debug("started");
	}

}