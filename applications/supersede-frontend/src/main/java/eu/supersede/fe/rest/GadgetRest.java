package eu.supersede.fe.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import eu.supersede.fe.application.ApplicationUtil;
import eu.supersede.fe.jpa.UserGadgetsJpa;
import eu.supersede.fe.model.UserGadget;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/gadget")
public class GadgetRest {

	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Autowired
    private UserGadgetsJpa userGadgets;

	private final static ApplicationGadgetComparator comparator = new ApplicationGadgetComparator();
	
	@RequestMapping("/available")
	public List<ApplicationGadget> getUserAuthenticatedAvailableApplicationsGadgets(Authentication auth)
	{
		DatabaseUser user = (DatabaseUser)auth.getPrincipal();
		List<String> authNames = new ArrayList<>();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		for(GrantedAuthority ga : authorities)
		{
			authNames.add(ga.getAuthority().substring(5));
		}
		
		List<ApplicationGadget> availGadgets = new ArrayList<>(applicationUtil.getApplicationsGadgetsByProfilesNames(authNames));
		List<UserGadget> gadgets = userGadgets.findByUserIdOrderByGadgetIdAsc(user.getUserId());
		
		//filterGadgets(availGadgets, gadgets);
		
		Collections.sort(availGadgets, comparator);
		
		return availGadgets;
	}
	
	private void filterGadgets(List<ApplicationGadget> availGadgets, List<UserGadget> gadgets)
	{
		for(int i = availGadgets.size() - 1; i >= 0; i--)
		{
			ApplicationGadget ag = availGadgets.get(i);
			
			for(int j = 0; j < gadgets.size(); j ++)
			{
				UserGadget ug = gadgets.get(j);
				if(ag.getApplicationName().equals(ug.getApplicationName()) && ag.getApplicationGadget().equals(ug.getGadgetName()))
				{
					availGadgets.remove(i);
					break;
				}
			}
		}
	}
	
	@RequestMapping("")
	public List<UserGadget> getUserAuthenticatedApplicationsGadgets(Authentication auth)
	{
		DatabaseUser user = (DatabaseUser)auth.getPrincipal();

		List<UserGadget> gadgets = userGadgets.findByUserIdOrderByGadgetIdAsc(user.getUserId());
		
		return gadgets;
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value="")
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

	@RequestMapping(value = "/panel", method = RequestMethod.GET)
	public Integer getNumPanels()
	{
		return 2;
	}
	
	@RequestMapping(value = "/panel", method = RequestMethod.PUT)
	public void setNumPanels(@RequestBody Integer numPanels)
	{
		
	}
}
