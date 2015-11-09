package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private NotificationsJpa notifies;
	
	@RequestMapping("/createForUsers")
	public void createForUsers(@RequestParam List<Long> users, @RequestParam String message) {
		notifier.createForUsers(users, message);
	}
	
	@RequestMapping("/createForRole")
	public void createForRole(@RequestParam String role, @RequestParam String message) {
		notifier.createForRole(role, message);
	}
	
	@RequestMapping("/get")
	public List<Notification> createForUsers(@RequestParam Long userId) {
		User u = users.findByUserId(userId);
		return notifies.findByUser(u);
	}
	
}
