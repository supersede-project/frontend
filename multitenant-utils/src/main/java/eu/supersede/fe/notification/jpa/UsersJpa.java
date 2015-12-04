package eu.supersede.fe.notification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.notification.model.User;

public interface UsersJpa extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	
}
