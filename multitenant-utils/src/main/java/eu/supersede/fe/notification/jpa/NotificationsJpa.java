package eu.supersede.fe.notification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.notification.model.Notification;

public interface NotificationsJpa extends JpaRepository<Notification, Long> {

}
