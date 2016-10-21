package eu.supersede.fe.security.redis.session;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.MapSession;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.stereotype.Component;

import eu.supersede.fe.security.DatabaseUser;
import eu.supersede.fe.security.redis.template.SessionObjectRedisTemplate;
import eu.supersede.fe.security.redis.template.SessionRedisTemplate;

@Component
public class AuthenticationSuccessListenerRedisSession implements ApplicationListener<SessionCreatedEvent> {

	@Autowired
	private SessionRedisTemplate sessionTemplate;
	
	@Autowired
	private SessionObjectRedisTemplate sessionObjectTemplate;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onApplicationEvent(SessionCreatedEvent event) {
		log.debug("created session id: " + event.getSessionId());
		
		MapSession ms = (MapSession) event.getSession();
		Session s = new Session();
		s.setId(ms.getId());
		s.setCreationTime(new Date(ms.getCreationTime()));
		s.setLastAccessTime(new Date(ms.getLastAccessedTime()));
		
		Object securetyContext = sessionObjectTemplate.opsForHash().get("spring:session:sessions:" + s.getId(), "sessionAttr:SPRING_SECURITY_CONTEXT");
		if(securetyContext != null)
		{
			SecurityContextImpl sCI = (SecurityContextImpl) securetyContext;
			DatabaseUser dbU = (DatabaseUser) sCI.getAuthentication().getPrincipal();
			s.setDatabaseUser(dbU);
		}
		
		sessionTemplate.opsForValue().set(Session.SUPERSEDE_SESSION_PREFIX + s.getId(), s);
	}

}
