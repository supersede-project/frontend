package eu.supersede.fe.notification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.notification.model.Profile;

public interface ProfilesJpa extends JpaRepository<Profile, Long> {

	Profile findByName(String name);
	
}
