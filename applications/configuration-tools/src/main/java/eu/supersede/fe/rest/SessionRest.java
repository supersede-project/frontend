package eu.supersede.fe.rest;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import eu.supersede.fe.template.SessionRedisTemplate;
import eu.supersede.fe.template.session.Session;

@RestController
@RequestMapping("/session")
public class SessionRest {
	
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionRedisTemplate sessionTemplate;
	
	@RequestMapping("")
	public Session getSession(HttpServletRequest request) throws IOException, ClassNotFoundException
	{
		Cookie cookie = WebUtils.getCookie(request, "SESSION");
		Session s = sessionTemplate.opsForValue().get(Session.SUPERSEDE_SESSION_PREFIX + cookie.getValue());
		
		return s;
	}
	
}
