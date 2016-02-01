package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.RequirementsMatricesDataJpa;
import demo.model.RequirementsMatrixData;

@RestController
@RequestMapping("/requirementsmatricesdata")
public class RequirementsMatrixDataRest {
	
	@Autowired
    private RequirementsMatricesDataJpa requirementsMatricesData;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<RequirementsMatrixData> getRequirementsMatricesData(){	
		
		return requirementsMatricesData.findAll();
	}
}
