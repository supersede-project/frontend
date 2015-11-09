package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Notification;
import demo.model.User;

public interface NotificationsJpa extends JpaRepository<Notification, Long> {

	List<Notification> findByUser(User u);
	
}
