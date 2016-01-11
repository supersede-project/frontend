package eu.supersede.fe.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Locale;
import eu.supersede.fe.model.User;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/locale")
public class LocaleRest {

	@Autowired
    private UsersJpa users;
	
	@SuppressWarnings("serial")
	private static final List<Locale> locales = new ArrayList<Locale>() 
	{{
		add(new Locale(new java.util.Locale("any")));
		add(new Locale(java.util.Locale.ENGLISH));
		add(new Locale(java.util.Locale.GERMAN));
		add(new Locale(new java.util.Locale("es")));
		add(new Locale(java.util.Locale.ITALIAN));
	}};
	
	@RequestMapping("")
	public List<Locale> getLocales() 
	{
		return locales;
	}
	
	@RequestMapping("/current")
	public Locale getCurrentLocale(Authentication auth)
	{
		Object user = auth.getPrincipal();
		String l = "any";
		
		if(user instanceof DatabaseUser)
		{
			DatabaseUser dbUser = (DatabaseUser)user;
			if(dbUser.getLocale() != null && !dbUser.getLocale().equals(""))
			{
				l = dbUser.getLocale();
			}
		}
		
		return new Locale(l);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/current")
	public void setCurrentLocale(Authentication auth, @RequestParam(required = true) String lang, HttpServletRequest request)
	{
		if(lang.equals("any"))
		{
			lang = "";
		}
		
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof DatabaseUser)
		{
			DatabaseUser dbUser = (DatabaseUser)user;
			dbUser.setLocale(lang);
			
			//updating principal in session
			Authentication authentication = new UsernamePasswordAuthenticationToken(dbUser, dbUser.getPassword(), dbUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			request.getSession(false).setAttribute(
					  HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					  SecurityContextHolder.getContext());
			
			User u = users.getOne(dbUser.getUserId());
			u.setLocale(lang);
			users.save(u);
		}
	}
}
