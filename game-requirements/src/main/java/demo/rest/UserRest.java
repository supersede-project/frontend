package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.UserCriteriaPointsJpa;
import demo.jpa.UsersJpa;
import demo.jpa.ValutationCriteriaJpa;
import demo.model.User;
import demo.model.ValutationCriteria;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	@Autowired
    private UsersJpa users;
	
	@Autowired
    private ValutationCriteriaJpa valutationCriterias;
	
	@Autowired
    private UserCriteriaPointsJpa userCriteriaPoints;
	
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
	
	// get the loggeduser
	@RequestMapping("/loggeduser")
	public User getLoggedUser(Authentication authentication)
	{
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User user = users.findOne(currentUser.getUserId());
		
		if(user == null)
		{
			throw new NotFoundException();
		}
		
		return user;
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
