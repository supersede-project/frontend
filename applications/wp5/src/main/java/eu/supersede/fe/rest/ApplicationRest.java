package eu.supersede.fe.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.application.ApplicationGadget;
import eu.supersede.fe.application.ApplicationGadgetComparator;
import eu.supersede.fe.application.ApplicationPage;
import eu.supersede.fe.application.ApplicationUtil;
import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.jpa.UserGadgetsJpa;
import eu.supersede.fe.model.Profile;
import eu.supersede.fe.model.UserGadget;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/application")
public class ApplicationRest {
		
	@Autowired
	private ApplicationUtil applicationUtil;

	@Autowired
    private ProfilesJpa profiles;
	
	@Autowired
    private UserGadgetsJpa userGadgets;

	private final static ApplicationGadgetComparator comparator = new ApplicationGadgetComparator();
	
	@RequestMapping("/availableGadget")
	public List<ApplicationGadget> getUserAuthenticatedAvailableApplicationsGadgets(Authentication auth)
	{
		List<String> authNames = new ArrayList<>();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority ga : authorities)
		{
			authNames.add(ga.getAuthority().substring(5));
		}
		
		List<ApplicationGadget> gadgets = new ArrayList<>(applicationUtil.getApplicationsGadgetsByProfilesNames(authNames));
	
		Collections.sort(gadgets, comparator);
		
		return gadgets;
	}
	
	@RequestMapping("/userGadget")
	public List<UserGadget> getUserAuthenticatedApplicationsGadgets(Authentication auth)
	{
		DatabaseUser user = (DatabaseUser)auth.getPrincipal();

		List<UserGadget> gadgets = userGadgets.findByUserIdOrderByGadgetIdAsc(user.getUserId());
		
		return gadgets;
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value="/userGadget")
	public List<UserGadget> saveUserAuthenticatedApplicationsGadgets(Authentication auth, @RequestBody List<UserGadget> gadgets)
	{
		DatabaseUser user = (DatabaseUser)auth.getPrincipal();
		Long userId = user.getUserId();
		
		for(long i = 0; i < gadgets.size(); i++)
		{
			UserGadget g = gadgets.get((int)i);
			g.setUserId(userId);
			g.setGadgetId(i);
		}
		
		userGadgets.deleteByUserId(userId);
		userGadgets.save(gadgets);
		
		return gadgets;
	}
	
	@RequestMapping("/page")
	public List<ProfileApplications> getUserAuthenticatedApplicationsPage(Authentication auth, Locale locale) 
	{
		String lang= locale.getLanguage();
		
		List<ProfileApplications>  r = new ArrayList<>();
		Map<String, List<Page>> pages = new HashMap<>();
		Map<String, String> labels = new HashMap<>();
		List<String> authNames = new ArrayList<>();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority ga : authorities)
		{
			authNames.add(ga.getAuthority().substring(5));
		}
		
		List<Profile> profList = profiles.findByNameIn(authNames);
		
		//make data nicer for frontend
		for(Profile p : profList)
		{
			
			ProfileApplications pa = new ProfileApplications();
			pa.setProfileName(p.getName());
			
			Set<ApplicationPage> apps = applicationUtil.getApplicationsPagesByProfileName(p.getName());
			for(ApplicationPage app : apps)
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
