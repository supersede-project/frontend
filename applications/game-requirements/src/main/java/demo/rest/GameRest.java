package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.GamesJpa;
import demo.model.Game;

@RestController
@RequestMapping("/game")
public class GameRest {

	@Autowired
    private GamesJpa games;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Game> getGames(){
	
		return games.findAll();
	}
}
