package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.GameCriteriasJpa;
import demo.model.GameCriteria;

@RestController
@RequestMapping("/gamecriteria")
public class GameCriteriaRest {

	@Autowired
    private GameCriteriasJpa gameCriterias;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<GameCriteria> getGameCriterias(){
	
		return gameCriterias.findAll();
	}
}
