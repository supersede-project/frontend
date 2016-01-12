package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.ProfilesJpa;
import demo.model.Profile;

@RestController
@RequestMapping("/profile")
public class ProfileRest {

	@Autowired
    private ProfilesJpa profiles;
	
	// get all the profiles
	@RequestMapping("")
	public List<Profile> getProfiles() {
		return profiles.findAll();
	}
	
	// get number of the profiles
	@RequestMapping("/count")
	public Long count() {
		return profiles.count();
	}	
}
