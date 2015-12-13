package eu.supersede.fe.application;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ApplicationUtil {

	private ObjectMapper mapper = new ObjectMapper();
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StringRedisTemplate template;
	
	private static final String APP_KEY = "Application";
	
	public void addApplicationPage(String applicationName, String applicationPage, String profileRequired)
	{
		Application app = new Application(applicationName, applicationPage, profileRequired);
		try 
		{
			template.opsForHash().put(APP_KEY, app.getId(), mapper.writeValueAsString(app));
		} 
		catch (JsonProcessingException e) 
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeApplicationPage(Application application)
	{
		template.opsForHash().delete(APP_KEY, application.getId());
	}
	
	public Set<Application> getAllApplications()
	{
		Set<Application> apps = new HashSet<>();
		Map<Object, Object> applications = template.opsForHash().entries("APP_KEY");
		
		for(Object o : applications.values())
		{
			apps.add(getApplicationFromString((String)o));
		}
		
		return apps;
	}
	
	public Set<Application> getByProfileName(String profile)
	{
		Set<Application> apps = new HashSet<>();
		Map<Object, Object> applications = template.opsForHash().entries("APP_KEY");
		
		for(Object o : applications.values())
		{
			Application a = getApplicationFromString((String)o);
			if(a.getProfileRequired().equals(profile))
			{
				apps.add(a);
			}
		}
		
		return apps;
	}
	
	private Application getApplicationFromString(String s)
	{
		try 
		{
			return mapper.readValue(s, Application.class);
		} 
		catch (JsonParseException e) 
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
