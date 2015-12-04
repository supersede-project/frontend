package eu.supersede.fe.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.supersede.fe.multitenant.MultiJpaProvider;
import eu.supersede.fe.notification.jpa.NotificationsJpa;
import eu.supersede.fe.notification.jpa.ProfilesJpa;
import eu.supersede.fe.notification.jpa.UsersJpa;
import eu.supersede.fe.notification.model.Notification;
import eu.supersede.fe.notification.model.Profile;
import eu.supersede.fe.notification.model.User;

@Component
public class NotificationUtil {

	@Autowired
	private MultiJpaProvider multiJpaProvider;

	@Autowired
	private NotificationsJpa notificationsJpa;

	@Autowired
	private UsersJpa usersJpa;
	
	@Autowired
	private ProfilesJpa profilesJpa;
	
	public void createNotificationForUser(String tenant, Long userId, String message)
	{
		User u = multiJpaProvider.getRepository(UsersJpa.class, tenant).getOne(userId);
		createNotificationForUser(multiJpaProvider.getRepository(NotificationsJpa.class, tenant), u, message);
	}
	
	public void createNotificationForUser(Long userId, String message)
	{
		createNotificationForUser(notificationsJpa, usersJpa.getOne(userId), message);
	}
	
	public void createNotificationForUser(String tenant, String email, String message)
	{
		User u = multiJpaProvider.getRepository(UsersJpa.class, tenant).findByEmail(email);
		createNotificationForUser(multiJpaProvider.getRepository(NotificationsJpa.class, tenant), u, message);
	}
	
	public void createNotificationForUser(String email, String message)
	{
		createNotificationForUser(notificationsJpa, usersJpa.findByEmail(email), message);
	}
	
	public void createNotificationForUser(String tenant, User u, String message)
	{
		createNotificationForUser(multiJpaProvider.getRepository(NotificationsJpa.class, tenant), u, message);
	}
	
	public void createNotificationForUser(User u, String message)
	{
		createNotificationForUser(notificationsJpa, u, message);
	}
	
	private void createNotificationForUser(NotificationsJpa nJpa, User u, String message)
	{
		Notification n = new Notification(message, u);
		nJpa.save(n);
	}
	
	public void createNotificationsForProfile(String tenant, String profile, String message)
	{
		createNotificationsForProfile(tenant, multiJpaProvider.getRepository(ProfilesJpa.class, tenant).findByName(profile), message);
	}
	
	public void createNotificationsForProfile(String profile, String message)
	{
		createNotificationsForProfile(profilesJpa.findByName(profile), message);
	}
	
	public void createNotificationsForProfile(Profile p, String message)
	{
		p = profilesJpa.getOne(p.getProfileId());
		List<User> users = p.getUsers();

		for(User u : users)
		{
			createNotificationForUser(notificationsJpa, u, message);
		}
	}
	
	public void createNotificationsForProfile(String tenant, Profile p, String message)
	{
		p = multiJpaProvider.getRepository(ProfilesJpa.class, tenant).getOne(p.getProfileId());
		List<User> users = p.getUsers();

		for(User u : users)
		{
			createNotificationForUser(multiJpaProvider.getRepository(NotificationsJpa.class, tenant), u, message);
		}
	}
}
