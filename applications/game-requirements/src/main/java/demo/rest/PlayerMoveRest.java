package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.PlayerMovesJpa;
import demo.jpa.UsersJpa;
import demo.model.PlayerMove;
import demo.model.User;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/playermove")
public class PlayerMoveRest {

	@Autowired
    private UsersJpa users;
	
	@Autowired
    private PlayerMovesJpa playerMoves;
	
	// get all the playerMoves of the logged user
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<PlayerMove> getPlayerMoves(Authentication authentication){	
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User player = users.findOne(currentUser.getUserId());
		
		return playerMoves.findByPlayer(player);
	}
	
	// get a specific playerMove 
	@RequestMapping("/{playerMoveId}")
	public PlayerMove getPlayerMove(@PathVariable Long playerMoveId){	
		
		PlayerMove playerMove = playerMoves.findOne(playerMoveId);
		
		if(playerMove == null)
		{
			throw new NotFoundException();
		}
		
		return playerMove;
	}
	
	// set the vote for of a player in his/her PlayerMove
	@RequestMapping(method = RequestMethod.PUT, value="/{playerMoveId}/vote/{vote}")
	public void setPlayerMoveVote(Authentication authentication, @PathVariable Long playerMoveId, @PathVariable Long vote){	
		
		PlayerMove playerMove = playerMoves.findOne(playerMoveId);
		playerMove.setValue(vote);
		playerMove.setPlayed(true);
		playerMoves.save(playerMove);
	}
}
