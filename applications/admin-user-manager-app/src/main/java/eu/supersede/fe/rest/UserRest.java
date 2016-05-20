package eu.supersede.fe.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wso2.carbon.user.core.UserStoreException;

import eu.supersede.fe.exception.InternalServerErrorException;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.integration.ProxyWrapper;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.jpa.UsersJpa;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.User;
import eu.supersede.fe.security.DatabaseUser;
import eu.supersede.integration.api.security.types.Role;

@RestController
@RequestMapping("/user")
public class UserRest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProxyWrapper proxy;

	@Autowired
	private UsersJpa users;

	@Autowired
	private ProfilesJpa profiles;

	// @PreAuthorize("hasAuthority('ADMIN')")
	// @Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(Authentication authentication, @RequestBody User user) {

		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		String tenant = currentUser.getTenantId();

		try {
			proxy.getIFAuthenticationManager(tenant).addUser(toSecurityUser(user, tenant), user.getPassword(), false);
		} catch (UserStoreException e) {
			log.error("IFAuthenticationManager thrown an exception: ");
			e.printStackTrace();
			throw new InternalServerErrorException();
		}

		// re-attach detached profiles
		List<Profile> ps = user.getProfiles();
		for (int i = 0; i < ps.size(); i++) {
			ps.set(i, profiles.findOne(ps.get(i).getProfileId()));
		}

		user = users.save(user);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}

	private eu.supersede.integration.api.security.types.User toSecurityUser(User u, String tenant) throws UserStoreException{
		eu.supersede.integration.api.security.types.User user = new eu.supersede.integration.api.security.types.User();

		user.setUserName(u.getEmail());
		user.setFirstname(u.getName());
		user.setLastname(u.getName());
		user.setEmail(u.getEmail());
    	
    	//Adding roles
    	Set<Role>roles = new HashSet<Role>();
    	Set<Role> allRoles = proxy.getIFAuthenticationManager(tenant).getAllRoles();
    	for (Role role: allRoles){
    		if (role.getRoleName().contains("Supersede")){
    			roles.add(role);
    		}
    	}
    	user.setRoles(roles);
		return user;
	}

	// @Secured({"ROLE_ADMIN"})
	@RequestMapping("/{userId}")
	public User getUser(@PathVariable Long userId) {
		User u = users.findOne(userId);
		if (u == null) {
			throw new NotFoundException();
		}

		return u;
	}

	// @Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getUsers() {
		return users.findAll();
	}

	// @Secured({"ROLE_ADMIN"})
	@RequestMapping("/count")
	public Long count() {
		return users.count();
	}
}
