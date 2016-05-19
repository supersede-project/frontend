package eu.supersede.fe.rest;

import java.util.List;

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

import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.integration.ProxyWrapper;
import eu.supersede.fe.security.DatabaseUser;
import eu.supersede.integration.api.datastore.fe.types.User;
import eu.supersede.integration.api.security.types.AuthorizationToken;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	@Autowired
	private ProxyWrapper proxy;
		
	//@PreAuthorize("hasAuthority('ADMIN')")
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(Authentication authentication, @RequestBody User user) {
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		String tenant = currentUser.getTenantId();
		AuthorizationToken token = currentUser.getToken();
		
		eu.supersede.integration.api.security.types.User userSec = new eu.supersede.integration.api.security.types.User();
		//TODO: set userSec data;
		
		proxy.getIFAuthenticationManager(tenant).addUser(userSec, "credential", false);
		proxy.getFEDataStoreProxy().save(user, tenant, token);
				
		user = users.save(user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("/{userId}")
	public User getUser(Authentication authentication, @PathVariable Long userId)
	{
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User u = proxy.getFEDataStoreProxy().getUser(currentUser.getTenantId(), userId.intValue(), false, currentUser.getToken());
		if(u == null)
		{
			throw new NotFoundException();
		}
		
		return u;
	}
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> getUsers(Authentication authentication) 
	{
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		return proxy.getFEDataStoreProxy().getUsers(currentUser.getTenantId(), false, currentUser.getToken());

	}
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("/count")
	public Integer count(Authentication authentication) {
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		return proxy.getFEDataStoreProxy().getUsers(currentUser.getTenantId(), true, currentUser.getToken()).size();
	}
}
