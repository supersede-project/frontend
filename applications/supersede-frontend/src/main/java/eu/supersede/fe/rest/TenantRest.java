package eu.supersede.fe.rest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant")
@PropertySource("file:../conf/multitenancy.properties")
public class TenantRest {

	@Value("#{'${application.multitenancy.names}'.split(',')}") 
	private String[] multitenancyNames;
	
	@PostConstruct
	private void load() {
		for(int i = 0; i < multitenancyNames.length; i++)
		{
			multitenancyNames[i] = multitenancyNames[i].trim();
		}
	}
	
	@RequestMapping("")
	public String[] getTenants() {
		return multitenancyNames;
	}
	
}
