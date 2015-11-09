package demo.notifier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.jpa.NotificationsJpa;
import demo.jpa.UsersJpa;
import demo.model.Notification;
import demo.model.User;

@Component
public class Notifier {

	@Autowired
    private UsersJpa users;
	
	@Autowired
    private NotificationsJpa notifies;
	
	public void createForUsers(List<Long> ids, String message) {
		for(Long id : ids)
		{
			User u = users.findByUserId(id);
			notifies.save(new Notification(message, u));
		}
	}

	public void createForRole(String role, String message) {
		List<User> us = users.findByRole(role);
		for(User u : us)
		{
			notifies.save(new Notification(message, u));
		}
		
	}

	
	
	
}
