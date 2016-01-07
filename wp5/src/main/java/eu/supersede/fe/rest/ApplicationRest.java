package eu.supersede.fe.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.application.Application;
import eu.supersede.fe.application.ApplicationUtil;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.model.Profile;

@RestController
@RequestMapping("/application")
public class ApplicationRest {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Autowired
    private ProfilesJpa profiles;
	
	@RequestMapping("")
	public List<ProfileApplications> getUserAuthenticatedApplications(Authentication auth, Locale locale) 
	{
		log.debug("Welcome home! The client locale is " + locale.toString());
		
		String lang= locale.getLanguage();
		
		List<ProfileApplications>  r = new ArrayList<>();
		Map<String, List<Page>> pages = new HashMap<>();
		Map<String, String> labels = new HashMap<>();
		List<String> authNames = new ArrayList<>();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority ga : authorities)
		{
			authNames.add(ga.getAuthority());
		}
		
		List<Profile> profList = profiles.findByNameIn(authNames);
		
		//make data nicer for frontend
		for(Profile p : profList)
		{
			
			ProfileApplications pa = new ProfileApplications();
			pa.setProfileName(p.getName());
			
			Set<Application> apps = applicationUtil.getByProfileName(p.getName());
			for(Application app : apps)
			{
				if(!pages.containsKey(app.getApplicationName()))
				{
					pages.put(app.getApplicationName(), new ArrayList<Page>());
					labels.put(app.getApplicationName(), app.getLocalizedApplicationLabel(lang));
				}
				pages.get(app.getApplicationName()).add(new Page(app.getApplicationPage(), app.getLocalizedApplicationPageLabel(lang)));
			}
			
			for(String app : pages.keySet())
			{
				ApplicationGrouped ag = new ApplicationGrouped();
				ag.setApplicationName(app);
				ag.setApplicationLabel(labels.get(app));
				
				for(Page pag : pages.get(app))
				{
					ag.getPages().add(pag);
				}
				
				pa.getApplications().add(ag);
			}
			
			r.add(pa);
			pages.clear();
			labels.clear();
		}
		
		return r;
	}
	
	static class ProfileApplications
	{
		private String profileName;
		private List<ApplicationGrouped> applications;
		
		public ProfileApplications() {
			applications = new ArrayList<>();
		}
		
		public String getProfileName() {
			return profileName;
		}
		
		public void setProfileName(String profileName) {
			this.profileName = profileName;
		}
		
		public List<ApplicationGrouped> getApplications() {
			return applications;
		}
		
		public void setApplications(List<ApplicationGrouped> applications) {
			this.applications = applications;
		}
	}
	
	static class ApplicationGrouped
	{
		private String applicationName;
		private String applicationLabel;
		private List<Page> pages;
		
		public ApplicationGrouped() {
			pages = new ArrayList<>();
		}
		
		public String getApplicationName() {
			return applicationName;
		}
		
		public void setApplicationName(String applicationName) {
			this.applicationName = applicationName;
		}

		public String getApplicationLabel() {
			return applicationLabel;
		}

		public void setApplicationLabel(String applicationLabel) {
			this.applicationLabel = applicationLabel;
		}
		
		public List<Page> getPages() {
			return pages;
		}
		
		public void setPages(List<Page> pages) {
			this.pages = pages;
		}
	}
	
	static class Page
	{
		private String name;
		private String label;
		
		Page(String name, String label)
		{
			this.setName(name);
			this.setLabel(label);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
}
