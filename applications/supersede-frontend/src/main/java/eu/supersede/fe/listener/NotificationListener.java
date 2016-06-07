package eu.supersede.fe.listener;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

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

	@SuppressWarnings("unused")
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
	
	
	private synchronized void loadNotifications()
	{
		multiJpaProvider.clearTenants();

		Map<String, ProfilesJpa> profiles = multiJpaProvider.getRepositories(ProfilesJpa.class);
		Map<String, NotificationsJpa> notifications = multiJpaProvider.getRepositories(NotificationsJpa.class);
		Map<String, UsersJpa> users = multiJpaProvider.getRepositories(UsersJpa.class);
		
		while (notificationTemplate.opsForSet().size("notifications") > 0L)
		{
			Notification n = notificationTemplate.opsForSet().pop("notifications");
			String tenant = n.getTenant();

			if(n.getProfile())
			{
				Profile p = profiles.get(tenant).findByName(n.getRecipient());
				for(User u : p.getUsers())
				{
					createNotification(users.get(tenant), notifications.get(tenant), u.getEmail(), n.getLink(), n.getMessage());
				}
			}
			else
			{
				createNotification(users.get(tenant), notifications.get(tenant), n.getRecipient(), n.getLink(), n.getMessage());
			}
		}
	}

	private void createNotification(UsersJpa users, NotificationsJpa notifications, String email, String link, String message)
	{
		User u = users.findByEmail(email);
		
		eu.supersede.fe.model.Notification notif = new eu.supersede.fe.model.Notification();
		notif.setCreationTime(new Date());
		notif.setEmailSent(false);
		notif.setLink(link);
		notif.setMessage(message);
		notif.setRead(false);
		notif.setUser(u);
		
		notifications.saveAndFlush(notif);
	}
	
	public void receiveMessage(String message) {
		loadNotifications();
    }
}
