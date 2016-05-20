package eu.supersede.fe.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import eu.supersede.integration.api.datastore.fe.types.Profile;
import eu.supersede.integration.api.datastore.fe.types.User;
import eu.supersede.integration.api.datastore.proxies.FEDataStoreProxy;
import eu.supersede.integration.api.security.IFAuthenticationManager;
import eu.supersede.integration.api.security.types.AuthorizationToken;

@Component
public class ProxyWrapper {

	private static List<String> tenants;
	private static Map<String, String> users;
	private static Map<String, String> passwords;
	
	private static FEDataStoreProxy proxy;
	private static Map<String, IFAuthenticationManager> ams;
	
	@PostConstruct
	public void load()
	{
		tenants = new ArrayList<>();
		tenants.add("atos");
		tenants.add("senercon");
		tenants.add("siemens");
		
		users = new HashMap<>();
		users.put("atos", "admin@atos.supersede.eu");
		users.put("senercon", "admin@senercon.supersede.eu");
		users.put("siemens", "admin@siemens.supersede.eu");
		
		passwords = new HashMap<>();
		passwords.put("atos", "rtgWCyrc");
		passwords.put("senercon", "J9JAQmvy");
		passwords.put("siemens", "pSMTykFC");
		
		proxy = new FEDataStoreProxy();
        
		ams = new HashMap<>();
		for(String t : tenants)
		{
			ams.put(t, new IFAuthenticationManager(users.get(t), passwords.get(t)));
		}
	}
	
	public FEDataStoreProxy getFEDataStoreProxy()
	{
		return proxy;
	}
	
	public IFAuthenticationManager getIFAuthenticationManager(String tenant)
	{
		return ams.get(tenant);
	}
	
	//TODO: functions to ask Josu to implement:
	public User getUserByName(String username, String tenantId, AuthorizationToken token)
	{
		User user = null;
		List<User> users = proxy.getUsers(tenantId, false, token);
		for(User u : users)
		{
			if(u.getEmail().equals(username))
			{
				user = u;
				break;
			}
		}
		
		return user;
	}
	
	public Profile getProfileByName(String profileName, String tenantId, AuthorizationToken token)
	{
		Profile profile = null;
		List<Profile> profiles = proxy.getProfiles(tenantId, token);
		for(Profile p : profiles)
		{
			if(p.getName().equals(profileName))
			{
				profile = p;
				break;
			}
		}
		
		return profile;
	}
	
	public List<Profile> getProfilesInNames(List<String> profileNames, String tenantId, AuthorizationToken token)
	{
		List<Profile> ret = new ArrayList<Profile>();
		
		List<Profile> profiles = proxy.getProfiles(tenantId, token);
		for(Profile p : profiles)
		{
			if(profileNames.contains(p.getName()) && !ret.contains(p))
			{
				ret.add(p);
			}
		}
		
		return ret;
	}
	
}
