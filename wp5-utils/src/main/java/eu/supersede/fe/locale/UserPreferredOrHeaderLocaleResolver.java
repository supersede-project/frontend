package eu.supersede.fe.locale;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import eu.supersede.fe.security.DatabaseUser;

public class UserPreferredOrHeaderLocaleResolver implements LocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Locale loc = request.getLocale();
		
		if ((authentication == null) || (authentication instanceof AnonymousAuthenticationToken)) {
            return loc;
        }
		
		if(SecurityContextHolder.getContext().getAuthentication() != null &&
			SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
		{
			Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user instanceof DatabaseUser)
			{
				DatabaseUser dbUser = (DatabaseUser)user;
				String l = dbUser.getLocale();
				if(l != null && !l.equals(""))
				{
					loc = new Locale.Builder().setLanguage(l).build();
				}
			}
		}
		
		return loc;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		throw new UnsupportedOperationException(
				"Cannot change locale - use a different locale resolution strategy");
	}

}
