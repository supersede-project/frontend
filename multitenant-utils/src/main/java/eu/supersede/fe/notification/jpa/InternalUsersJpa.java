package eu.supersede.fe.notification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.notification.model.InternalUser;

public interface InternalUsersJpa extends JpaRepository<InternalUser, Long> {
	
	InternalUser findByEmail(String email);
	
}
