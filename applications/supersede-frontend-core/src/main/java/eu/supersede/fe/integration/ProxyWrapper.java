/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.integration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import eu.supersede.integration.api.datastore.fe.types.Profile;
import eu.supersede.integration.api.datastore.fe.types.User;
import eu.supersede.integration.api.datastore.proxies.FEDataStoreProxy;
import eu.supersede.integration.api.security.IFAuthenticationManager;
import eu.supersede.integration.api.security.types.AuthorizationToken;

@Component
@PropertySources({
	@PropertySource("file:../conf/multitenancy.properties"),
	@PropertySource("file:../conf/if.properties")
})
public class ProxyWrapper {
	
	@Autowired
	Environment env;
	
	private String[] tenants;
	
	private static Map<String, AuthManagerUser> users;
	
	private static FEDataStoreProxy proxy;
	private static Map<String, IFAuthenticationManager> ams;
	
	@PostConstruct
	public void load()
	{
		users = new HashMap<>();
		ams = new HashMap<>();
		tenants = env.getRequiredProperty("application.multitenancy.names").split(",");
		
		for(int i = 0; i < tenants.length; i++)
		{
			tenants[i] = tenants[i].trim();
		}
		
		for(String t : tenants)
		{
			String[] pair = env.getRequiredProperty("is.authorization." + t + ".tenant.pair").split("/");
			String domain = env.getRequiredProperty("is.authorization." + t + ".tenant.domain");
			
			String password = pair[1];
			String user = pair[0] + domain;
			
			AuthManagerUser authUser = new AuthManagerUser(user, password);
			users.put(t, authUser);
			ams.put(t, new IFAuthenticationManager(authUser.user, authUser.password));
		}
		
		proxy = new FEDataStoreProxy();
        
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
		List<User> users = null;
		try
		{
			users = proxy.getUsers(tenantId, false, token);
		}
		catch(URISyntaxException ex)
		{
			return null;
		}
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
	
	private class AuthManagerUser
	{
		private String user;
		private String password;
		
		public AuthManagerUser(String user, String password) {
			this.user = user;
			this.password = password;
		}
	}
	
}
