package eu.supersede.fe.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.jpa.ApplicationsJpa;
import eu.supersede.fe.model.Application;

@RestController
@RequestMapping("/application")
public class ApplicationRest {

	@Autowired
    private ApplicationsJpa applications;
	
	@RequestMapping("")
	public List<Application> getAll(@RequestParam(required=false) Long profileId) 
	{
		List<Application> r;
		if(profileId == null)
		{
			r = applications.findAll();
		}
		else
		{
			r = applications.findByRequiredProfileId(profileId);
		}
		
		return r;
	}
}
