package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.CriteriasMatricesDataJpa;
import demo.model.CriteriasMatrixData;

@RestController
@RequestMapping("/criteriasmatricesdata")
public class CriteriasMatrixDataRest {

	@Autowired
    private CriteriasMatricesDataJpa criteriasMatricesData;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<CriteriasMatrixData> getCriteriasMatricesData(){
	
		return criteriasMatricesData.findAll();
	}
}
