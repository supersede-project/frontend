package eu.supersede.fe.notification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.notification.model.InternalNotification;

public interface InternalNotificationsJpa extends JpaRepository<InternalNotification, Long> {

}
