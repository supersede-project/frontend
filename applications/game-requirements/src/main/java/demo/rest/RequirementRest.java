package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.RequirementsJpa;
import demo.model.Requirement;
import eu.supersede.fe.exception.NotFoundException;

@RestController
@RequestMapping("/requirement")
public class RequirementRest {
	
	@Autowired
    private RequirementsJpa requirements;
	
	// all the requirements
	@RequestMapping("")
	public List<Requirement> getRequirements() {
		return requirements.findAll();
	}
	
	// get a specific requirement 
	@RequestMapping("/{requirementId}")
	public Requirement getRequirement(@PathVariable Long requirementId)
	{
		Requirement c = requirements.findOne(requirementId);
		if(c == null)
		{
			throw new NotFoundException();
		}
		
		return c;
	}
	
	// get number of requirements
	@RequestMapping("/count")
	public Long count() {
		return requirements.count();
	}
	
	// create new requirement
	@RequestMapping(value = "/create/{name}/description/{description}", method = RequestMethod.PUT)
	public void createCriteria(@PathVariable String name, @PathVariable String description)
	{
		Requirement r = new Requirement();
		r.setName(name);
		r.setDescription(description);
		requirements.save(r);
	}
	
	// edit requirement
	@RequestMapping(value = "/edit/{requirementId}/name/{name}/description/{description}", method = RequestMethod.PUT)
	public void editRequirement(@PathVariable Long requirementId, @PathVariable String name, @PathVariable String description)
	{
		Requirement requirement = requirements.findOne(requirementId);
		requirement.setName(name);
		requirement.setDescription(description);
		requirements.save(requirement);
	}
}