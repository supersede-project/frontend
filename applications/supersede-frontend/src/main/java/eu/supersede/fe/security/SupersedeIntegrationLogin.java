package eu.supersede.fe.security;

import java.util.List;

import eu.supersede.integration.api.datastore.fe.types.User;
import eu.supersede.integration.api.datastore.proxies.FEDataStoreProxy;
import eu.supersede.integration.api.security.IFAuthenticationManager;
import eu.supersede.integration.api.security.types.AuthorizationToken;
import eu.supersede.integration.properties.IntegrationProperty;

public class SupersedeIntegrationLogin {

	private static FEDataStoreProxy userProxy;
	private static IFAuthenticationManager am;
	
	static {
		userProxy = new FEDataStoreProxy();
		String admin = IntegrationProperty.getProperty("is.admin.user");
		String password = IntegrationProperty.getProperty("is.admin.passwd");
        am = new IFAuthenticationManager(admin, password);
	}
	
	public AuthorizationToken getToken(String username, String password)
	{	
		AuthorizationToken token = null;
		
		try
		{
			token = am.getAuthorizationToken(username, password);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
        return token;
	}
	
	public User getUser(String username, String tenantId, AuthorizationToken token)
	{
		//TODO: ask Yosu for a getUserByName function
    	//User user = userProxy.getUser(tenantId, username, false, token);
    	//return user;
		
		User user = null;
		List<User> users = userProxy.getUsers(tenantId, false, token);
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
	
}
