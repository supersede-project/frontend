package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.NotificationsJpa;
import demo.jpa.UsersJpa;
import demo.model.Notification;
import demo.model.User;
import demo.notifier.Notifier;

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
	
	@RequestMapping("/createForRole")
	public void createForRole(@RequestParam(required=true) String role, @RequestParam(required=true) String message) 
	{
		notifier.createForRole(role, message);
	}
	
	@RequestMapping("")
	public List<Notification> getByUserId(@RequestParam(required=true) Long userId, @RequestParam(defaultValue="true") Boolean toRead)
	{
		User u = users.findByUserId(userId);
		List<Notification> ns;
		if(toRead)
		{
			ns = notifications.findByUserAndRead(u, !toRead);
		}
		else
		{
			ns = notifications.findByUser(u);
		}
		
		return ns;
	}
	
	@RequestMapping("/read")
	public void setRead(@RequestParam(required=true) Long notificationId) {
		Notification n = notifications.findByNotificationId(notificationId);
		n.setRead(true);
		notifications.save(n);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void delete(@RequestParam(required=true) Long notificationId)
	{
		notifications.delete(notificationId);
	}
}
