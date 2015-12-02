package demo.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import demo.exception.NotFoundException;
import demo.jpa.MovesJpa;
import demo.jpa.UsersJpa;
import demo.model.Move;
import demo.model.User;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/move")
public class MoveRest {
	
	@Autowired
    private MovesJpa moves;
	
	@Autowired
    private UsersJpa users;
	
	// all the moves for the logged user
	@RequestMapping(value = "",  method = RequestMethod.GET)
	public List<Move> getMoves(Authentication authentication) {
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User user = users.findOne(currentUser.getUserId());
		List<Move> playerMoves = moves.findByFirstPlayerOrSecondPlayer(user, user);
			
		return playerMoves;	
	}
	
	// number of total moves
	@RequestMapping("/count")
	public Long count() {
		return moves.count();
	}
	
	// get a specific move 
	@RequestMapping("/{moveId}")
	public Move getMove(@PathVariable Long moveId)
	{
		Move c = moves.findOne(moveId);
		if(c == null)
		{
			throw new NotFoundException();
		}
		
		return c;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createMove(@RequestBody Move move) {
		
		move.setStartTime(new Date());
		move = moves.save(move);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(move.getMoveId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
}
