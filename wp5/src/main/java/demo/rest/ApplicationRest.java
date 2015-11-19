package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.ApplicationsJpa;
import demo.model.Application;

@RestController
@RequestMapping("/application")
public class ApplicationRest {

	@Autowired
    private ApplicationsJpa applications;
	
	@RequestMapping("")
	public List<Application> getAll(@RequestParam(required=false) String role) 
	{
		List<Application> r;
		if(role == null)
		{
			r = applications.findAll();
		}
		else
		{
			r = applications.findByRequiredRole(role);
		}
		
		return r;
	}
}