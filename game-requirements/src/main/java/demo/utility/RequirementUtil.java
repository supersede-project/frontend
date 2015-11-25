package demo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.jpa.RequirementsJpa;
import demo.model.Requirement;

@Component
public class RequirementUtil {

	@Autowired
	private RequirementsJpa requirements;
	
	public void createRequirement(String content)
	{		
		Requirement req = new Requirement(content);
		requirements.save(req);
	}
	
	public void populateRequirements()
	{		
		for(int i=0;i<10;i++)
		{
			Requirement req = new Requirement("Requirement "+i);
			requirements.save(req);
		}
	}	
}
