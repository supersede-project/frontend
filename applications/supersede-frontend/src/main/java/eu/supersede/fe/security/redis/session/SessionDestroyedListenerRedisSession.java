package eu.supersede.fe.security.redis.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import eu.supersede.fe.security.redis.template.SessionRedisTemplate;

@Component
public class SessionDestroyedListenerRedisSession implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	private SessionRedisTemplate sessionTemplate;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		log.debug("destroyed session id: " + event.getSessionId());
		
		sessionTemplate.delete(Session.SUPERSEDE_SESSION_PREFIX + event.getSessionId());
	}

}
