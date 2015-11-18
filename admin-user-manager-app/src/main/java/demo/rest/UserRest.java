package demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import demo.exception.NotFoundException;
import demo.jpa.UsersJpa;
import demo.model.User;

@RestController
@RequestMapping("/user")
public class UserRest {

	private static List<String> roles = new ArrayList<String>() {{add("user"); add("admin");}};
	
	@Autowired
    private UsersJpa users;
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		user.setUserId(null);
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
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("/roles")
	public List<String> getRoles() {
		return roles;
	}
}
