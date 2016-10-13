package eu.supersede.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import eu.supersede.fe.security.DatabaseUser;
import eu.supersede.model.SessionContent;
import eu.supersede.template.SessionRedisTemplate;

@RestController
@RequestMapping("/session")
public class SessionRest {
	
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionRedisTemplate redisTemplate;
	
	@RequestMapping("")
	public SessionContent getSession(HttpServletRequest request) throws IOException, ClassNotFoundException
	{
		SessionContent sc = new SessionContent();
		Cookie cookie = WebUtils.getCookie(request, "SESSION");
		sc.setId(cookie.getValue());
		
		Object securetyContext = redisTemplate.opsForHash().get("spring:session:sessions:" + sc.getId(), "sessionAttr:SPRING_SECURITY_CONTEXT");
		if(securetyContext != null)
		{
			SecurityContextImpl s = (SecurityContextImpl) securetyContext;
			DatabaseUser dbU = (DatabaseUser) s.getAuthentication().getPrincipal();
			sc.setDatabaseUser(dbU);
		}
		
		sc.setCreationTime(new Date((Long) redisTemplate.opsForHash().get("spring:session:sessions:" + sc.getId(), "creationTime")));

		sc.setLastAccessTime(new Date((Long) redisTemplate.opsForHash().get("spring:session:sessions:" + sc.getId(), "lastAccessedTime")));
		
		return sc;
	}
	
}
