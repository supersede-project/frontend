package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.JudgeActsJpa;
import demo.jpa.PlayerMovesJpa;
import demo.jpa.RequirementsMatricesDataJpa;
import demo.jpa.UsersJpa;
import demo.model.JudgeAct;
import demo.model.PlayerMove;
import demo.model.RequirementsMatrixData;
import demo.model.User;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.security.DatabaseUser;

@RestController
@RequestMapping("/judgeact")
public class JudgeActRest {

	@Autowired
    private JudgeActsJpa judgeActs;
	
	@Autowired
    private UsersJpa users;

	@Autowired
    private RequirementsMatricesDataJpa requirementsMatricesData;
	
	@Autowired
    private PlayerMovesJpa playerMoves;
	
	// get all the judgeActs if user is a judge
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(value = "",  method = RequestMethod.GET)
	public List<JudgeAct> getJudgeActs() {

		return judgeActs.findAll();
	}
	
	// get a specific judgeAct 
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(value = "/{judgeActId}", method = RequestMethod.GET)
	public JudgeAct getJudgeAct(@PathVariable Long judgeActId){	
				
		JudgeAct ja = judgeActs.findOne(judgeActId);
		
		if(ja == null)
		{
			throw new NotFoundException();
		}
		
		return ja;
	}
	
	// set the vote for of a judge in his/her judgeAct
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(method = RequestMethod.PUT, value="/{judgeActId}/vote/{vote}")
	public void setjudgeActVote(Authentication authentication, @PathVariable Long judgeActId, @PathVariable Long vote){	
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User judge = users.findOne(currentUser.getUserId());
		
		JudgeAct judgeAct = judgeActs.findOne(judgeActId);
		
		judgeAct.setJudge(judge);
		judgeAct.setVoted(true);
		judgeActs.save(judgeAct);
		
		RequirementsMatrixData requirementsMatrixData = judgeAct.getRequirementsMatrixData();		
		requirementsMatrixData.setValue(vote);
		requirementsMatricesData.save(requirementsMatrixData);
		
		// set played true to all player_moves connected with the requirementsMatrixDataId
		
		List<PlayerMove> playerMovesList = playerMoves.findByRequirementsMatrixData(requirementsMatrixData);
		for(int i=0; i< playerMovesList.size();i++){
			playerMovesList.get(i).setPlayed(true);
			playerMoves.save(playerMovesList.get(i));
		}
	}
}
