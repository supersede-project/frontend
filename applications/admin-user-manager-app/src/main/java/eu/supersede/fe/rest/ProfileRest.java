package eu.supersede.fe.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.integration.ProxyWrapper;
import eu.supersede.fe.security.DatabaseUser;
import eu.supersede.integration.api.datastore.fe.types.Profile;

@RestController
@RequestMapping("/profile")
public class ProfileRest {

	@Autowired
	private ProxyWrapper proxy;
	
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping("")
	public List<Profile> getProfiles(Authentication authentication) {
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		List<Profile> profiles = proxy.getFEDataStoreProxy().getProfiles(currentUser.getTenantId(), currentUser.getToken());
		return profiles;
	}
}
