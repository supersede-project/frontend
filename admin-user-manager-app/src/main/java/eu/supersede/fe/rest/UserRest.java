package eu.supersede.fe.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.User;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	private static final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

	@Autowired
    private UsersJpa users;
	
	@Autowired
    private ProfilesJpa profiles;
	
	//@PreAuthorize("hasAuthority('ADMIN')")
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
	
		//remove id and encrypt password
		user.setUserId(null);
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		
		//re-attach detached profiles
		List<Profile> ps = user.getProfiles();
		for(int i = 0; i < ps.size(); i++)
		{
			ps.set(i, profiles.findOne(ps.get(i).getProfileId()));
		}
		
		user = users.save(user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("/{userId}")
	public User getUser(@PathVariable Long userId)
	{
		User u = users.findOne(userId);
		if(u == null)
		{
			throw new NotFoundException();
		}
		
		return u;
	}
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getUsers() 
	{
		return users.findAll();
	}
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("/count")
	public Long count() {
		return users.count();
	}
}
