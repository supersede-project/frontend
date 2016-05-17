package eu.supersede.fe.security.oauth2;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class OAuth2Login {
	
	
	//TODO: remove this once token provider has a valid certificate
	static {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
		    public X509Certificate[] getAcceptedIssuers(){return null;}
		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("TLS");
		    sc.init(null, trustAllCerts, new SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		    ;
		}
	}
	
	private static final String ACCESS_TOKEN = "access_token";
	private static final String CONSUMER_KEY = "64JBUTGQn5dRJ9LgpIuifyPnTwka";
	private static final String CONSUMER_SECRET = "zKvM53iFpVdbaRX7gZfeJw3TgGoa";
	private static final String ACCESS_ENDPOINT = "https://localhost:9443/oauth2/token";
	private static final String SCOPE = "default";
	
	public String getToken(String username, String password)
	{	
		String accessToken = null;
		
		try
		{
			OAuthClientRequest accessRequest = OAuthClientRequest.tokenLocation(ACCESS_ENDPOINT)
	                .setGrantType(GrantType.PASSWORD)
	                .setClientId(CONSUMER_KEY)
	                .setClientSecret(CONSUMER_SECRET)
	                .setScope(SCOPE)
	                .setUsername(username)
	                .setPassword(password)
	                .buildBodyMessage();
	
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
	        OAuthClientResponse oAuthResponse = oAuthClient.accessToken(accessRequest);
	        accessToken = oAuthResponse.getParam(ACCESS_TOKEN);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
        return accessToken;
	}
	
}
