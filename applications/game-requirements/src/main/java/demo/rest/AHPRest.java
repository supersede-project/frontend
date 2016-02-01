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
		//input.setCriteria( "c1", "c2" );
		List<String> criteriasList = new ArrayList<>();
		for(int i=0; i<g.getCriterias().size();i++){
			criteriasList.add(g.getCriterias().get(i).getCriteriaId().toString());
		}		
		input.setCriteria(criteriasList);
		//##################################################################
		
		//ROW - COL - VALUE
		//##################################################################
		// set the preferences on couple of criteria
		//input.setPreference( "c1", "c2", 4 );
		List<CriteriasMatrixData> criteriasMatrixDataList = criteriasMatricesData.findByGame(g);
		for(int i=0; i<criteriasMatrixDataList.size();i++){
			input.setPreference( criteriasMatrixDataList.get(i).getRowCriteria().getCriteriaId().toString(), 
					criteriasMatrixDataList.get(i).getColumnCriteria().getCriteriaId().toString(), 
					(int) (long) criteriasMatrixDataList.get(i).getValue() );
		}	
		//##################################################################
		
		
		//##################################################################
		// set the requirements of the game
		//input.setOptions( "o1", "o2", "o3" );
		List<String> requirementsList = new ArrayList<>();
		for(int i=0; i<g.getRequirements().size();i++){
			requirementsList.add(g.getRequirements().get(i).getRequirementId().toString());
		}		
		input.setOptions(requirementsList);
		//##################################################################

		
		//ROW - COL - CRITERIA - VALUE
		//##################################################################
		// set the preferences between two requirements of a specific criteria
		/*input.setOptionPreference( "o1", "o2", "c1", 0 );
		input.setOptionPreference( "o1", "o3", "c1", 4 );
		input.setOptionPreference( "o2", "o3", "c1", 0 );
		*/
		List<RequirementsMatrixData> requirementsMatrixDataList = requirementsMatricesData.findByGame(g);
		for(int i=0; i<requirementsMatrixDataList.size();i++){
			if((int) (long) requirementsMatrixDataList.get(i).getValue() != -1){
				input.setOptionPreference(requirementsMatrixDataList.get(i).getRowRequirement().getRequirementId().toString(), 
						requirementsMatrixDataList.get(i).getColumnRequirement().getRequirementId().toString(), 
						requirementsMatrixDataList.get(i).getCriteria().getCriteriaId().toString(),
						(int) (long) requirementsMatrixDataList.get(i).getValue());
			}
		}		
		//##################################################################

		
		//##################################################################		
		// set the preferences between two requirements of a specific criteria
		/*input.setOptionPreference( "o1", "o2", "c2", 0 );
		input.setOptionPreference( "o1", "o3", "c2", 4 );
		input.setOptionPreference( "o2", "o3", "c2", 8 );
		*/
		//##################################################################

		
		AHPAnalyser analyser = new AHPAnalyser();
		
		Map<String,Double> map = analyser.eval( input );
		return map;
	}
}
