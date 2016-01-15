package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.GameRequirementsJpa;
import demo.model.GameRequirement;

@RestController
@RequestMapping("/gamerequirement")
public class GameRequirementRest {

	@Autowired
    private GameRequirementsJpa gameRequirements;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<GameRequirement> getGameRequirements(){
	
		return gameRequirements.findAll();
	}
}
