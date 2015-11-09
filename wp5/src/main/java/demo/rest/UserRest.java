package demo.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.UsersJpa;
import demo.model.User;

@RestController
@RequestMapping("/user")
public class UserRest {

	@Autowired
    private UsersJpa users;

	@RequestMapping("")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/create")
	@Secured({"ROLE_ADMIN"})
	public void createUser() {
		users.save(new User("Matteo", "matteo@gmail.com", "test", "user"));
	}
	
	@RequestMapping("/count")
	public Long count() {
		return users.count();
	}
	
	@RequestMapping("/getAll")
	public List<User> getAll() {
		return users.findAll();
	}
}
