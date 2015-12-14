package eu.supersede.fe.application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applications.properties")
public class ApplicationConfigLoader {

	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void load()
	{
		String applicationName = env.getProperty("application.name");
		String applicationLabel = env.getProperty("application.label");
		
		if(applicationName != null)
		{
			String[] pageNames = env.getRequiredProperty("application.pages").split(",");
			
			for(String pageName : pageNames)
			{
				pageName = pageName.trim();
				
				String[] pageRoles = env.getRequiredProperty("application.page." + pageName + ".profiles").split(",");
				String pageLabel = env.getRequiredProperty("application.page." + pageName + ".label");
				for(String pageRole : pageRoles)
				{
					pageRole = pageRole.trim();
					applicationUtil.addApplicationPage(applicationName, applicationLabel, pageName, pageLabel, pageRole);
				}
			}
		}
	}
	
}
