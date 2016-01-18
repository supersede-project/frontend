package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.CriteriasMatricesJpa;
import demo.model.CriteriasMatrix;

@RestController
@RequestMapping("/criteriasmatrices")
public class CriteriasMatrixRest {

	@Autowired
    private CriteriasMatricesJpa criteriasMatrices;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<CriteriasMatrix> getCriteriasMatrices(){
	
		return criteriasMatrices.findAll();
	}
}
