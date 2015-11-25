package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.GamesJpa;
import demo.model.Game;

@RestController
@RequestMapping("/game")
public class GameRest {
	@Autowired
    private GamesJpa games;
	
	// all the games
	@RequestMapping("")
	public List<Game> getGames() {
		return games.findAll();
	}
	
	// number of games
	@RequestMapping("/count")
	public Long count() {
		return games.count();
	}
	
	// create a game
	@RequestMapping("/create")
	public void createRequirement(@RequestParam(required=true) String name,@RequestParam(required=true) int timer) 
	{		
		Game game = new Game(name,timer);
		games.save(game);
	}
}
