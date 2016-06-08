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

package eu.supersede.fe.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import eu.supersede.fe.message.ProfileRedisTemplate;
import eu.supersede.fe.message.model.Profile;

@Component
@PropertySource("classpath:wp5_application.properties")
public class ApplicationConfigLoader {

	private static final String[] langs = {"en", "de", "es", "it"};
	
	@Autowired
	private ProfileRedisTemplate profileTemplate;
	
	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void load()
	{
		Set<String> requiredProfiles = new HashSet<String>();
		
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
			
			String home = env.getRequiredProperty("application.home");
			applicationUtil.addApplication(applicationName, applicationLabels, home);
			
			String[] pageNames = env.getRequiredProperty("application.pages").split(",");
			
			for(String pageName : pageNames)
			{
				Map<String, String> pageLabels = new HashMap<>();
				List<String> trimmedPageProfiles = new ArrayList<>();
				pageName = pageName.trim();
				
				String[] pageProfiles = env.getRequiredProperty("application.page." + pageName + ".profiles").split(",");
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
				
				for(String pageProfile : pageProfiles)
				{
					String p = pageProfile.trim();
					trimmedPageProfiles.add(p);
				}
				
				requiredProfiles.addAll(trimmedPageProfiles);
				applicationUtil.addApplicationPage(applicationName, pageName, pageLabels, trimmedPageProfiles);
			}
			
			String[] gadgets = env.getProperty("application.gadgets").split(",");
			if(gadgets != null)
			{
				for(String gadget : gadgets)
				{
					List<String> trimmedGadgetProfiles = new ArrayList<>();
					gadget = gadget.trim();
				
					String[] gadgetProfiles = env.getRequiredProperty("application.gadgets." + gadget + ".profiles").split(",");
					
					for(String gadgetProfile : gadgetProfiles)
					{
						trimmedGadgetProfiles.add(gadgetProfile.trim());
					}
					
					requiredProfiles.addAll(trimmedGadgetProfiles);
					applicationUtil.addApplicationGadget(applicationName, gadget, trimmedGadgetProfiles);
				}
			}
			
			createProfiles(requiredProfiles);
		}
	}
	
	private void createProfiles(Set<String> profiles)
	{
		if(profiles.size() > 0)
		{
			for(String p : profiles)
			{
				Profile prof = new Profile();
				prof.setName(p);
				profileTemplate.opsForSet().add("profiles", prof);
			}
			profileTemplate.convertAndSend("profile", "");
		}
	}
}
