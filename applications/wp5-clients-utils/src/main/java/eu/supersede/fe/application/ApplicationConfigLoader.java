package eu.supersede.fe.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:wp5_application.properties")
public class ApplicationConfigLoader {

	private static final String[] langs = {"en", "de", "es", "it"};
	
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
			Map<String, String> applicationLabels = new HashMap<>();
			applicationLabels.put("", applicationLabel);
			for(String lang : langs)
			{
				String localApplicationLabel = env.getProperty("application.label." + lang);
				if(localApplicationLabel != null)
				{
					applicationLabels.put(lang, localApplicationLabel);
				}
			}
			
			String[] pageNames = env.getRequiredProperty("application.pages").split(",");
			
			for(String pageName : pageNames)
			{
				Map<String, String> pageLabels = new HashMap<>();
				List<String> trimmedPageRoles = new ArrayList<>();
				pageName = pageName.trim();
				
				String[] pageRoles = env.getRequiredProperty("application.page." + pageName + ".profiles").split(",");
				String pageLabel = env.getRequiredProperty("application.page." + pageName + ".label");
				pageLabels.put("", pageLabel);
				
				for(String lang : langs)
				{
					String localPageLabel = env.getProperty("application.page." + pageName + ".label." + lang);
					if(localPageLabel != null)
					{
						pageLabels.put(lang, localPageLabel);
					}
				}
				
				for(String pageRole : pageRoles)
				{
					trimmedPageRoles.add(pageRole.trim());
				}
				
				applicationUtil.addApplicationPage(applicationName, applicationLabels, pageName, pageLabels, trimmedPageRoles);
			}
		}
	}
	
}
