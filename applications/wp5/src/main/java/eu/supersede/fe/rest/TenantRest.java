package eu.supersede.fe.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant")
@PropertySource("classpath:wp5.properties")
public class TenantRest {

	@Value("#{'${spring.multitenancy.names}'.split(',')}") 
	private String[] multitenancyNames;
	
	@RequestMapping("")
	public String[] getTenants() {
		return multitenancyNames;
	}
	
}
