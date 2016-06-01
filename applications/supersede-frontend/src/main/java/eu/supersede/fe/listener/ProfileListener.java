package eu.supersede.fe.listener;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.supersede.fe.jpa.ProfilesJpa;
import eu.supersede.fe.message.ProfileRedisTemplate;
import eu.supersede.fe.message.model.Profile;
import eu.supersede.fe.multitenant.MultiJpaProvider;

@Component
public class ProfileListener {

	@Autowired
	private ProfileRedisTemplate profileTemplate;
	
	@Autowired
	private MultiJpaProvider multiJpaProvider;
	
	@PostConstruct
	private void init()
	{
		loadProfiles();
	}
	
	private void loadProfiles()
	{
		Map<String, ProfilesJpa> profilesJpa = multiJpaProvider.getRepositories(ProfilesJpa.class);

		multiJpaProvider.clearTenants();
		
		while (profileTemplate.opsForSet().size("profiles") > 0L)
		{
			Profile p = profileTemplate.opsForSet().pop("profiles");
			for(ProfilesJpa profiles : profilesJpa.values())
			{
				if(p.getName() != null && !p.getName().equals("") && profiles.findByName(p.getName()) == null)
				{
					eu.supersede.fe.model.Profile prof = new eu.supersede.fe.model.Profile();
					prof.setName(p.getName());
					profiles.save(prof);
				}
			}
		}
	}
	
	public void receiveMessage(String message) {
		loadProfiles();
    }
}
