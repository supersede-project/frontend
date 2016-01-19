package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.RequirementsMatricesJpa;
import demo.model.RequirementsMatrix;

@RestController
@RequestMapping("/requirementsmatrices")
public class RequirementsMatrixRest {

	@Autowired
    private RequirementsMatricesJpa requirementsMatrices;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<RequirementsMatrix> getRequirementsMatrices(){
	
		return requirementsMatrices.findAll();
	}
}
