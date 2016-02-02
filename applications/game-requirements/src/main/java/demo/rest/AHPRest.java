package demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.CriteriasMatricesDataJpa;
import demo.jpa.GamesJpa;
import demo.jpa.RequirementsMatricesDataJpa;
import demo.model.CriteriasMatrixData;
import demo.model.Game;
import demo.model.RequirementsMatrixData;
import eu.supersede.algos.AHPAnalyser;
import eu.supersede.algos.AHPStructure;
import eu.supersede.fe.exception.NotFoundException;

@RestController
@RequestMapping("/ahp")
public class AHPRest {

	@Autowired
    private GamesJpa games;
	@Autowired
    private CriteriasMatricesDataJpa criteriasMatricesData;
	@Autowired
    private RequirementsMatricesDataJpa requirementsMatricesData;
	
	// AHP alghoritm for a specific game
	@RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
	public Map<String,Double> getValuesFromAlgorithm(@PathVariable Long gameId) {
		
		Game g = games.findOne(gameId);
		
		if(g == null){
			throw new NotFoundException();
		}
		
		AHPStructure input = new AHPStructure();
		
		//##################################################################
		// set the criterias of the game
		List<String> criteriasList = new ArrayList<>();
		for(int i=0; i<g.getCriterias().size();i++){
			criteriasList.add(g.getCriterias().get(i).getCriteriaId().toString());
		}		
		input.setCriteria(criteriasList);
		//##################################################################
		
		//ROW - COL - VALUE
		//##################################################################
		// set the preferences on couple of criteria
		List<CriteriasMatrixData> criteriasMatrixDataList = criteriasMatricesData.findByGame(g);
		for(int i=0; i<criteriasMatrixDataList.size();i++){
			input.setPreference( criteriasMatrixDataList.get(i).getRowCriteria().getCriteriaId().toString(), 
					criteriasMatrixDataList.get(i).getColumnCriteria().getCriteriaId().toString(), 
					criteriasMatrixDataList.get(i).getValue().intValue());
		}	
		//##################################################################
		
		
		//##################################################################
		// set the requirements of the game
		List<String> requirementsList = new ArrayList<>();
		for(int i=0; i<g.getRequirements().size();i++){
			requirementsList.add(g.getRequirements().get(i).getRequirementId().toString());
		}		
		input.setOptions(requirementsList);
		//##################################################################

		
		//ROW - COL - CRITERIA - VALUE
		//##################################################################
		// set the preferences between two requirements of a specific criteria
		List<RequirementsMatrixData> requirementsMatrixDataList = requirementsMatricesData.findByGame(g);
		for(int i=0; i<requirementsMatrixDataList.size();i++){
			if((int) (long) requirementsMatrixDataList.get(i).getValue() != -1){
				input.setOptionPreference(requirementsMatrixDataList.get(i).getRowRequirement().getRequirementId().toString(), 
						requirementsMatrixDataList.get(i).getColumnRequirement().getRequirementId().toString(), 
						requirementsMatrixDataList.get(i).getCriteria().getCriteriaId().toString(),
						requirementsMatrixDataList.get(i).getValue().intValue());
			}
		}		
		//##################################################################

		AHPAnalyser analyser = new AHPAnalyser();
		
		Map<String,Double> map = analyser.eval( input );
		return map;
	}
}
