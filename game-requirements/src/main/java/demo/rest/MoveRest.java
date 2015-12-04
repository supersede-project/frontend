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

import demo.jpa.MovesJpa;
import demo.jpa.RequirementsJpa;
import demo.jpa.UsersJpa;
import demo.model.Move;
import demo.model.Requirement;
import demo.model.User;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.notification.NotificationUtil;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/move")
public class MoveRest {
	
	@Autowired
    private MovesJpa moves;
	
	@Autowired
    private UsersJpa users;
	
	@Autowired
    private RequirementsJpa requirements;
	
	@Autowired
    private NotificationUtil notificationUtil;
	
	// get all the moves for the logged user
	@RequestMapping(value = "",  method = RequestMethod.GET)
	public List<Move> getMoves(Authentication authentication) {
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User user = users.findOne(currentUser.getUserId());
		
		List<Move> playerMoves = moves.findByFirstPlayerOrSecondPlayer(user, user);
		
		//List<Move> playerMoves = moves.findSpecial(user);
			
		return playerMoves;	
	}
	
	// get number of total moves
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
	
	// post for the creation of a move
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createMove(@RequestBody Move move) {
		
		move.setStartTime(new Date());
		move = moves.save(move);

		notificationUtil.createNotificationForUser(move.getFirstPlayer().getUserId(), "New move " + move.getMoveId());
		notificationUtil.createNotificationForUser(move.getSecondPlayer().getUserId(), "New move " + move.getMoveId());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(move.getMoveId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
	// put, for update the requirement value with the player
	@RequestMapping(method = RequestMethod.PUT, value="/{moveId}/requirement/{requirementId}")
	public void setRequirement(Authentication authentication, @PathVariable Long moveId, @PathVariable Long requirementId) {
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User user = users.findOne(currentUser.getUserId());
		
		Move m = moves.findOne(moveId);
		Requirement r = requirements.findOne(requirementId);
		
		if(m.getFirstPlayer().getUserId() == user.getUserId())
		{
			m.setFirstPlayerChooseRequirement(r);
			if(m.getSecondPlayerChooseRequirement() != null)
			{
				m.setFinish(true);
			}
		}
		else
		{
			m.setSecondPlayerChooseRequirement(r);
			if(m.getFirstPlayerChooseRequirement() != null)
			{
				m.setFinish(true);
			}
		}
		
		moves.save(m);
	}
		
}
