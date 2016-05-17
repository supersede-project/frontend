package eu.supersede.fe.security;

import org.springframework.util.Assert;

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
	
	public String getToken(String username, String password)
	{	
		String accessToken = null;
		
		try
		{
			AuthorizationToken token = am.getAuthorizationToken(username, password);
			accessToken = token.getAccessToken();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
        return accessToken;
	}
	
	public User getUser(String username, String tenantId, String token)
	{
    	User user = userProxy.getUser(tenantId, username, false, token);
    	return user;
	}
	
}
