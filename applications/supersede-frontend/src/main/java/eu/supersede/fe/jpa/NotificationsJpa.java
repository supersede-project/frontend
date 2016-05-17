package eu.supersede.fe.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.Notification;
import eu.supersede.fe.model.User;

public interface NotificationsJpa extends JpaRepository<Notification, Long> {

	List<Notification> findByUserOrderByCreationTimeDesc(User u);

	List<Notification> findByUserAndReadOrderByCreationTimeDesc(User u, boolean b);
	
	Long countByUser(User u);
	
	Long countByUserAndRead(User u, boolean b);

	List<Notification> findByReadAndEmailSentAndCreationTimeLessThan(boolean b, boolean c, Date limit);
	
}
