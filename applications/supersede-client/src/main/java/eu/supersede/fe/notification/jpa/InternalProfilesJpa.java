package eu.supersede.fe.notification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.notification.model.InternalProfile;

public interface InternalProfilesJpa extends JpaRepository<InternalProfile, Long> {

	InternalProfile findByName(String name);
	
}
