package eu.supersede.fe.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.Profile;

public interface ProfilesJpa extends JpaRepository<Profile, Long> {
	
	Profile findByName(String name);

	List<Profile> findByNameIn(List<String> authNames);
	
}
