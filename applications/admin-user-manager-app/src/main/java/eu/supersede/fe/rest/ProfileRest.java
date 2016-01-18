package eu.supersede.fe.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.model.Profile;

@RestController
@RequestMapping("/profile")
public class ProfileRest {

	@Autowired
    private ProfilesJpa profiles;
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("")
	public List<Profile> getProfiles() {
		return profiles.findAll();
	}
}
