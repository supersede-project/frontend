package demo.rest;

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

import demo.exception.NotFoundException;
import demo.jpa.ProfilesJpa;
import demo.jpa.UserCriteriaPointsJpa;
import demo.jpa.UsersJpa;
import demo.jpa.ValutationCriteriaJpa;
import demo.model.Profile;
import demo.model.User;
import demo.model.ValutationCriteria;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	private static final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

	@Autowired
    private UsersJpa users;
	
	@Autowired
    private ProfilesJpa profiles;
	
	@Autowired
    private ValutationCriteriaJpa valutationCriterias;
	
	@Autowired
    private UserCriteriaPointsJpa userCriteriaPoints;
	
	// create a new user
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
	
	// get a specific user by the Id
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
	
	// get all the users
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getUsers() 
	{
		return users.findAll();
	}
	
	// Get all users that have a specific ValutationCriteria
	@RequestMapping(value = "/criteria/{criteriaId}", method = RequestMethod.GET)
	public List<User> getCriteriaUsers(@PathVariable Long criteriaId)
	{
		ValutationCriteria v = valutationCriterias.findOne(criteriaId);
		if(v == null){
			throw new NotFoundException();
		}
		
		List<User> userList = userCriteriaPoints.findUsersByValutationCriteria(v);		
		return userList;
	}
	
	// get the number of total users
	@RequestMapping("/count")
	public Long count() {
		return users.count();
	}
}
