package demo.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.algos.AHPAnalyser;
import eu.supersede.algos.AHPStructure;

@RestController
@RequestMapping("/ahp")
public class AHPRest {

	// values from AHP alghoritm
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String,Double> getValuesFromAlgorithm() {
		
		AHPStructure input = new AHPStructure();
		
		input.setCriteria( "c1", "c2" );
		input.setPreference( "c1", "c2", 4 );
		
		input.setOptions( "o1", "o2", "o3" );
		input.setOptionPreference( "o1", "o2", "c1", 0 );
		input.setOptionPreference( "o1", "o3", "c1", 4 );
		input.setOptionPreference( "o2", "o3", "c1", 0 );
		
		input.setOptionPreference( "o1", "o2", "c2", 0 );
		input.setOptionPreference( "o1", "o3", "c2", 4 );
		input.setOptionPreference( "o2", "o3", "c2", 8 );	
		
		AHPAnalyser analyser = new AHPAnalyser();
		
		Map<String,Double> map = analyser.eval( input );
		return map;
	}
}
