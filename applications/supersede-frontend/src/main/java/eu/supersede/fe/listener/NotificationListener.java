package eu.supersede.fe.listener;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.supersede.fe.jpa.NotificationsJpa;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.message.NotificationRedisTemplate;
import eu.supersede.fe.message.model.Notification;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.User;
import eu.supersede.fe.multitenant.MultiJpaProvider;

@Component
public class NotificationListener {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NotificationRedisTemplate notificationTemplate;
	
	@Autowired
	private MultiJpaProvider multiJpaProvider;
	
	@PostConstruct
	private void init()
	{
		loadNotifications();
	}
	
	@Transactional
	private void loadNotifications()
	{
		multiJpaProvider.clearTenants();
		while (notificationTemplate.opsForSet().size("notifications") > 0L)
		{
			Notification n = notificationTemplate.opsForSet().pop("notifications");
			String tenant = n.getTenant();
			
			if(n.getProfile())
			{
				ProfilesJpa profiles = multiJpaProvider.getRepository(ProfilesJpa.class, tenant);
				Profile p = profiles.findByName(n.getRecipient());
				for(User u : p.getUsers())
				{
					createNotification(tenant, u.getEmail(), n.getLink(), n.getMessage());
				}
			}
			else
			{
				createNotification(tenant, n.getRecipient(), n.getLink(), n.getMessage());
			}
		}
	}

	@Transactional
	private void createNotification(String tenant, String email, String link, String message)
	{
		log.debug("Create notif: " + message + " to: " + email);
		NotificationsJpa notifications = multiJpaProvider.getRepository(NotificationsJpa.class, tenant);
		UsersJpa users = multiJpaProvider.getRepository(UsersJpa.class, tenant);
		
		User u = users.findByEmail(email);
		
		eu.supersede.fe.model.Notification notif = new eu.supersede.fe.model.Notification();
		notif.setCreationTime(new Date());
		notif.setEmailSent(false);
		notif.setLink(link);
		notif.setMessage(message);
		notif.setRead(false);
		notif.setUser(u);
		
		notifications.save(notif);
	}
	
	public void receiveMessage(String message) {
		loadNotifications();
    }
}
