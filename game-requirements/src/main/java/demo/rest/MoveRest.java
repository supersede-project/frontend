package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.MovesJpa;
import demo.model.Move;
import demo.model.Requirement;
import demo.model.User;

@RestController
@RequestMapping("/move")
public class MoveRest {
	@Autowired
    private MovesJpa moves;
	
	// all the games
	@RequestMapping("")
	public List<Move> getGames() {
		return moves.findAll();
	}
	
	// number of games
	@RequestMapping("/count")
	public Long count() {
		return moves.count();
	}
	
	// create a game
	@RequestMapping("/create")
	public void createRequirement(@RequestParam(required=true) String name,
			@RequestParam(required=true) int timer, 
			@RequestParam(required=true) Requirement firstRequirement, 
			@RequestParam(required=true) Requirement secondRequirement, 
			@RequestParam(required=true) User firstPlayer, 
			@RequestParam(required=true) User secondPlayer) 
	{		
		Move move = new Move(name,timer, firstRequirement, secondRequirement, firstPlayer, secondPlayer);
		moves.save(move);
	}
}
