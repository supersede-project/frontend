package eu.supersede.fe.locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class LocaleConfiguration {

	@Bean
	public LocaleResolver localeResolver() {
		//AcceptHeaderLocaleResolver  slr = new AcceptHeaderLocaleResolver ();
		UserPreferredOrHeaderLocaleResolver lr = new UserPreferredOrHeaderLocaleResolver();
	    return lr;
	}
}
