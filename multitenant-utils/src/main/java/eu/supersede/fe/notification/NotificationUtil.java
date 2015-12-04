package eu.supersede.fe.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.supersede.fe.multitenant.MultiJpaProvider;
import eu.supersede.fe.notification.jpa.InternalNotificationsJpa;
import eu.supersede.fe.notification.jpa.InternalProfilesJpa;
import eu.supersede.fe.notification.jpa.InternalUsersJpa;
import eu.supersede.fe.notification.model.InternalNotification;
import eu.supersede.fe.notification.model.InternalProfile;
import eu.supersede.fe.notification.model.InternalUser;

@Component
public class NotificationUtil {

	@Autowired
	private MultiJpaProvider multiJpaProvider;

	@Autowired
	private InternalNotificationsJpa internalNotificationsJpa;

	@Autowired
	private InternalUsersJpa internalUsersJpa;
	
	@Autowired
	private InternalProfilesJpa internalProfilesJpa;
	
	public void createNotificationForUser(String tenant, Long userId, String message)
	{
		InternalUser u = multiJpaProvider.getRepository(InternalUsersJpa.class, tenant).getOne(userId);
		createNotificationForUser(multiJpaProvider.getRepository(InternalNotificationsJpa.class, tenant), u, message);
	}
	
	public void createNotificationForUser(Long userId, String message)
	{
		createNotificationForUser(internalNotificationsJpa, internalUsersJpa.getOne(userId), message);
	}
	
	public void createNotificationForUser(String tenant, String email, String message)
	{
		InternalUser u = multiJpaProvider.getRepository(InternalUsersJpa.class, tenant).findByEmail(email);
		createNotificationForUser(multiJpaProvider.getRepository(InternalNotificationsJpa.class, tenant), u, message);
	}
	
	public void createNotificationForUser(String email, String message)
	{
		createNotificationForUser(internalNotificationsJpa, internalUsersJpa.findByEmail(email), message);
	}
	
	private void createNotificationForUser(InternalNotificationsJpa nJpa, InternalUser u, String message)
	{
		InternalNotification n = new InternalNotification(message, u);
		nJpa.save(n);
	}
	
	public void createNotificationsForProfile(String tenant, String profile, String message)
	{
		createNotificationsForProfile(tenant, multiJpaProvider.getRepository(InternalProfilesJpa.class, tenant).findByName(profile), message);
	}
	
	public void createNotificationsForProfile(String profile, String message)
	{
		createNotificationsForProfile(internalProfilesJpa.findByName(profile), message);
	}
	
	private void createNotificationsForProfile(InternalProfile p, String message)
	{
		p = internalProfilesJpa.getOne(p.getProfileId());
		List<InternalUser> users = p.getUsers();

		for(InternalUser u : users)
		{
			createNotificationForUser(internalNotificationsJpa, u, message);
		}
	}
	
	private void createNotificationsForProfile(String tenant, InternalProfile p, String message)
	{
		p = multiJpaProvider.getRepository(InternalProfilesJpa.class, tenant).getOne(p.getProfileId());
		List<InternalUser> users = p.getUsers();

		for(InternalUser u : users)
		{
			createNotificationForUser(multiJpaProvider.getRepository(InternalNotificationsJpa.class, tenant), u, message);
		}
	}
}
