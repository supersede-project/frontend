package eu.supersede.fe.application;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

	private static final String PAGE_KEY = "ApplicationPage";
	private static final String GADGET_KEY = "ApplicationGadget";
	
	//Application Page
	
	public void addApplicationPage(String applicationName, Map<String, String> applicationLabels, String applicationPage, Map<String, String> applicationPageLabels, List<String> profilesRequired)
	{
		ApplicationPage app = new ApplicationPage(applicationName, applicationLabels, applicationPage, applicationPageLabels, profilesRequired);
		try 
		{
			template.opsForHash().put(PAGE_KEY, app.getId(), mapper.writeValueAsString(app));
		} 
		catch (JsonProcessingException e) 
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeApplicationPage(ApplicationPage page)
	{
		template.opsForHash().delete(PAGE_KEY, page.getId());
	}
	
	public Set<ApplicationPage> getAllApplicationsPages()
	{
		Set<ApplicationPage> apps = new HashSet<>();
		List<Object> applications = template.opsForHash().values(PAGE_KEY);
		
		for(Object o : applications)
		{
			apps.add(getApplicationPageFromString((String)o));
		}
		
		return apps;
	}
	
	public Set<ApplicationPage> getApplicationsPagesByProfileName(String profile)
	{
		Set<ApplicationPage> apps = new HashSet<>();
		List<Object> applications = template.opsForHash().values(PAGE_KEY);
		
		for(Object o : applications)
		{
			ApplicationPage a = getApplicationPageFromString((String)o);
			if(a.getProfilesRequired().contains(profile))
			{
				apps.add(a);
			}
		}
		
		return apps;
	}
	
	private ApplicationPage getApplicationPageFromString(String s)
	{
		try 
		{
			return mapper.readValue(s, ApplicationPage.class);
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

	//Application Gadget
	
	public void addApplicationGadget(String applicationName, String applicationGadget, List<String> profilesRequired)
	{
		ApplicationGadget gadget = new ApplicationGadget(applicationName, applicationGadget, profilesRequired);
		try 
		{
			template.opsForHash().put(GADGET_KEY, gadget.getId(), mapper.writeValueAsString(gadget));
		} 
		catch (JsonProcessingException e) 
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeApplicationGadget(ApplicationGadget gadget)
	{
		template.opsForHash().delete(GADGET_KEY, gadget.getId());
	}

	public Set<ApplicationGadget> getAllApplicationsGadget()
	{
		Set<ApplicationGadget> gadgs = new HashSet<>();
		List<Object> gadgets = template.opsForHash().values(GADGET_KEY);
		
		for(Object o : gadgets)
		{
			gadgs.add(getApplicationGadgetFromString((String)o));
		}
		
		return gadgs;
	}
	
	public Set<ApplicationGadget> getApplicationsGadgetsByProfileName(String profile)
	{
		Set<ApplicationGadget> gadgs = new HashSet<>();
		List<Object> gadgets = template.opsForHash().values(GADGET_KEY);
		
		for(Object o : gadgets)
		{
			ApplicationGadget g = getApplicationGadgetFromString((String)o);
			if(g.getProfilesRequired().contains(profile))
			{
				gadgs.add(g);
			}
		}
		
		return gadgs;
	}
	
	public Set<ApplicationGadget> getApplicationsGadgetsByProfilesNames(Collection<String> profiles)
	{
		Set<ApplicationGadget> gadgs = new HashSet<>();
		List<Object> gadgets = template.opsForHash().values(GADGET_KEY);
		
		for(Object o : gadgets)
		{
			ApplicationGadget g = getApplicationGadgetFromString((String)o);
			if(!Collections.disjoint(g.getProfilesRequired(), profiles))
			{
				gadgs.add(g);
			}
		}
		
		return gadgs;
	}
	
	private ApplicationGadget getApplicationGadgetFromString(String s)
	{
		try 
		{
			return mapper.readValue(s, ApplicationGadget.class);
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
