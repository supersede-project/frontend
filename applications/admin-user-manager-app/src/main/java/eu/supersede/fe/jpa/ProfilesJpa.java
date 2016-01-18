package eu.supersede.fe.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.Profile;

public interface ProfilesJpa extends JpaRepository<Profile, Long> {
	
	Profile findByName(String name);
	
}
