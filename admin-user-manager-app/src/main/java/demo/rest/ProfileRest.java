package demo.rest;

import java.util.ArrayList;
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
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("")
	public List<String> getProfiles() {
		List<Profile> ps = profiles.findAll();
		
		List<String> ret = new ArrayList<String>();
		for(Profile p : ps)
		{
			ret.add(p.getName());
		}
		
		return ret;
	}
}
