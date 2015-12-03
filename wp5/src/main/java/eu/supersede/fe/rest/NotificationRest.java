package eu.supersede.fe.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.jpa.NotificationsJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Notification;
import eu.supersede.fe.model.User;
import eu.supersede.fe.notifier.Notifier;

@RestController
@RequestMapping("/notification")
public class NotificationRest {

	@Autowired
    private Notifier notifier;

	@Autowired
    private UsersJpa users;

	@Autowired
    private NotificationsJpa notifications;
	
	@RequestMapping("/createForUsers")
	public void createForUsers(@RequestParam(required=true) List<String> usersEmail, @RequestParam(required=true) String message) 
	{
		notifier.createForUsers(usersEmail, message);
	}
	
	@RequestMapping("/createForProfile")
	public void createForRole(@RequestParam(required=true) String profile, @RequestParam(required=true) String message) 
	{
		notifier.createForProfile(profile, message);
	}
	
	@RequestMapping("")
	public List<Notification> getByUserId(Authentication authentication, @RequestParam(defaultValue="true") Boolean toRead)
	{
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		User u = users.findByEmail(currentUser.getUsername());
		List<Notification> ns;
		if(toRead)
		{
			ns = notifications.findByUserAndReadOrderByCreationTimeDesc(u, !toRead);
		}
		else
		{
			ns = notifications.findByUserOrderByCreationTimeDesc(u);
		}
		
		return ns;
	}
	
	@RequestMapping("/count")
	public Long countByUserId(Authentication authentication, @RequestParam(defaultValue="true") Boolean toRead)
	{
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		User u = users.findByEmail(currentUser.getUsername());
		Long c;
		if(toRead)
		{
			c = notifications.countByUserAndRead(u, !toRead);
		}
		else
		{
			c = notifications.countByUser(u);
		}
		
		return c;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{notificationId}/read")
	public void setRead(@PathVariable Long notificationId) {
		Notification n = notifications.findOne(notificationId);
		n.setRead(true);
		notifications.save(n);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{notificationId}")
	public void delete(@PathVariable Long notificationId)
	{
		notifications.delete(notificationId);
	}
}