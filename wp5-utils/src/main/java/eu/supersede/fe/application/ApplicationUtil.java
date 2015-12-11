package eu.supersede.fe.application;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {

	@Autowired
	private StringRedisTemplate template;
	
	private static final String APP_KEY = "Application";
	
	public void addApplicationPage(String applicationName, String applicationPage, String profileRequired)
	{
		Application app = new Application(applicationName, applicationPage, profileRequired);
		template.opsForHash().put(APP_KEY, app.getId(), app);
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
			apps.add((Application)o);
		}
		
		return apps;
	}
	
}
