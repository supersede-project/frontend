package eu.supersede.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import eu.supersede.model.SessionContent;

@RestController
@RequestMapping("/session")
public class SessionRest {
	
	@RequestMapping("")
	public SessionContent getSession(HttpServletRequest request)
	{
		SessionContent sc = new SessionContent();
		Cookie cookie = WebUtils.getCookie(request, "SESSION");
		sc.setId(cookie.getValue());
		return sc;
	}
	
}
