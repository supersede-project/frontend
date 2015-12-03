package eu.supersede.fe.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.jpa.ApplicationsJpa;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.model.Application;
import eu.supersede.fe.model.Profile;

@RestController
@RequestMapping("/application")
public class ApplicationRest {

	@Autowired
    private ApplicationsJpa applications;
	
	@Autowired
    private ProfilesJpa profiles;
	
	@RequestMapping("")
	public List<ProfileApplications> getUserAuthenticatedApplications(Authentication auth) 
	{
		List<ProfileApplications>  r = new ArrayList<>();
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
			
			Map<String, List<String>> tmp = new HashMap<>(); 
			List<Application> apps = applications.findByRequiredProfileId(p.getProfileId());
			
			for(Application app : apps)
			{
				if(!tmp.containsKey(app.getName()))
				{
					tmp.put(app.getName(), new ArrayList<String>());
				}
				tmp.get(app.getName()).add(app.getMainPage());
			}
			
			for(String app : tmp.keySet())
			{
				ApplicationGrouped ag = new ApplicationGrouped();
				ag.setApplicationName(app);
				
				for(String pag : tmp.get(app))
				{
					ag.getPages().add(pag);
				}
				
				pa.getApplications().add(ag);
			}
			
			r.add(pa);
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
		private List<String> pages;
		
		public ApplicationGrouped() {
			pages = new ArrayList<>();
		}
		
		public String getApplicationName() {
			return applicationName;
		}
		
		public void setApplicationName(String applicationName) {
			this.applicationName = applicationName;
		}
		
		public List<String> getPages() {
			return pages;
		}
		
		public void setPages(List<String> pages) {
			this.pages = pages;
		}
	}
	
}
