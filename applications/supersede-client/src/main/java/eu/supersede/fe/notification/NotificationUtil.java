package eu.supersede.fe.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import eu.supersede.fe.message.NotificationRedisTemplate;
import eu.supersede.fe.message.model.Notification;
import eu.supersede.fe.security.DatabaseUser;

@Component
public class NotificationUtil {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NotificationRedisTemplate notificationTemplate;

	private String getCurrentTenant()
	{
		String tenant = null;
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext != null)
		{
			Authentication authentication = securityContext.getAuthentication();
			if(authentication != null)
			{
				DatabaseUser userDetails = (DatabaseUser)authentication.getPrincipal();
				tenant = userDetails.getTenantId();
			}	
		}
		return tenant;
	}
	
	public void createNotificationForUser(String email, String message, String link)
	{
		createNotificationForUser(getCurrentTenant(), email, message, link);
	}
	
	public void createNotificationForUser(String tenant, String email, String message, String link)
	{
		if(tenant == null)
		{
			log.error("Tenant can not be null");
			return;
		}
		
		Notification n = new Notification(tenant, email, false, message, link);
		notificationTemplate.opsForSet().add("notifications", n);
		notificationTemplate.convertAndSend("notification", "");
	}
	
	public void createNotificationsForProfile(String profile, String message, String link)
	{
		createNotificationsForProfile(getCurrentTenant(), profile, message, link);
	}
	
	public void createNotificationsForProfile(String tenant, String profile, String message, String link)
	{
		if(tenant == null)
		{
			log.error("Tenant can not be null");
			return;
		}
		
		Notification n = new Notification("tenant", profile, true, message, link);
		notificationTemplate.opsForSet().add("notifications", n);
		notificationTemplate.convertAndSend("notification", "");
	}
	
}
