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

import demo.jpa.JudgeMovesJpa;
import demo.jpa.MovesJpa;
import demo.jpa.PointsJpa;
import demo.jpa.RequirementsJpa;
import demo.jpa.UserCriteriaPointsJpa;
import demo.jpa.UserPointsJpa;
import demo.jpa.UsersJpa;
import demo.model.JudgeMove;
import demo.model.Move;
import demo.model.Requirement;
import demo.model.User;
import demo.model.UserCriteriaPoint;
import demo.model.UserPoint;
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
	
	@Autowired
	private JudgeMovesJpa judgeMoves;
	
	@Autowired
	private UserPointsJpa userPoints;
	
	@Autowired
	private PointsJpa points;
	
	@Autowired
	private UserCriteriaPointsJpa userCriteriaPoints;
	
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
		move.setToPlay(true);
		move = moves.save(move);

		notificationUtil.createNotificationForUser(move.getFirstPlayer().getUserId(), "New move " + move.getMoveId(), "game-requirements/user_moves");
		notificationUtil.createNotificationForUser(move.getSecondPlayer().getUserId(), "New move " + move.getMoveId(), "game-requirements/user_moves");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(move.getMoveId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
	// put, for update the requirement value with the player and set the winner
	@RequestMapping(method = RequestMethod.PUT, value="/{moveId}/requirement/{requirementId}")
	public void setRequirement(Authentication authentication, @PathVariable Long moveId, @PathVariable Long requirementId) {
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User user = users.findOne(currentUser.getUserId());
		
		Move m = moves.findOne(moveId);
		Requirement r = requirements.findOne(requirementId);
		
		if(m.getFirstPlayer().getUserId() == user.getUserId())
		{
			m.setFirstPlayerChooseRequirement(r);
			
			// set the 'voted' points
			UserPoint up = user.getUserGlobalPoints();
			Long globalPoints = up.getUserPoints();
			up.setUserPoints(globalPoints + points.getOne((long) -1).getGlobalPoints());
			userPoints.save(up);
			
			if(m.getSecondPlayerChooseRequirement() != null)
			{
				//set the winner requirement if they are equals
				if(m.getSecondPlayerChooseRequirement().getRequirementId() == r.getRequirementId())
				{
					// set the 'both players agree' points
					Long otherGlobalPoints = up.getUserPoints();
					up.setUserPoints(otherGlobalPoints + points.getOne((long) -2).getGlobalPoints());
					userPoints.save(up);
									
					// set the criteria points
					boolean find = false;
					List<UserCriteriaPoint> lucp = user.getUserCriteriaPoints();
					for(int i=0; i< lucp.size(); i++)
					{
						if(lucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
						{
							Long criteriaPoints = lucp.get(i).getPoints();
							lucp.get(i).setPoints(criteriaPoints + points.getOne((long) -2).getCriteriaPoints());
							find = true;
							userCriteriaPoints.save(lucp.get(i));
						}
					}
					
					if(find == false){
						UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -2).getCriteriaPoints(), m.getCriteria(), user);
						userCriteriaPoints.save(ucp);
					}
					
					//#####################################################################################################################
					// SET TO THE OTHER PLAYER
					User otherPlayer = m.getSecondPlayer();
					UserPoint upSecond = otherPlayer.getUserGlobalPoints();
					Long otherPlayerGlobalPoints = upSecond.getUserPoints();
					upSecond.setUserPoints(otherPlayerGlobalPoints + points.getOne((long) -2).getGlobalPoints());
					
					find = false;
					List<UserCriteriaPoint> otherlucp = otherPlayer.getUserCriteriaPoints();
					for(int i=0; i< otherlucp.size(); i++)
					{
						if(otherlucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
						{
							Long secondPlayerCriteriaPoints = otherlucp.get(i).getPoints();
							otherlucp.get(i).setPoints(secondPlayerCriteriaPoints + points.getOne((long) -2).getCriteriaPoints());
							find = true;
							userCriteriaPoints.save(otherlucp.get(i));
						}
					}
					
					if(find == false){
						UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -2).getCriteriaPoints(), m.getCriteria(), otherPlayer);
						userCriteriaPoints.save(ucp);
						find = true;
					}
					
					//#####################################################################################################################
					
					
					// set the winner requirement
					m.setWinnerRequirement(r);
				}
				m.setFinish(true);
			}
		}
		else
		{
			m.setSecondPlayerChooseRequirement(r);
			
			// set the 'voted' points
			UserPoint up = user.getUserGlobalPoints();
			Long globalPoints = up.getUserPoints();
			up.setUserPoints(globalPoints + points.getOne((long) -1).getGlobalPoints());
			userPoints.save(up);
			
			if(m.getFirstPlayerChooseRequirement() != null)
			{
				//set the winner requirement if they are equals
				if(m.getFirstPlayerChooseRequirement().getRequirementId() == r.getRequirementId())
				{
					// set the 'both players agree' points
					Long otherGlobalPoints = up.getUserPoints();
					up.setUserPoints(otherGlobalPoints + points.getOne((long) -2).getGlobalPoints());
					userPoints.save(up);
					
					// set the criteria points
					boolean find = false;
					// set criteria points for the SELECTED USER
					List<UserCriteriaPoint> lucp = user.getUserCriteriaPoints();
					for(int i=0; i< lucp.size(); i++)
					{
						if(lucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
						{
							Long criteriaPoints = lucp.get(i).getPoints();
							lucp.get(i).setPoints(criteriaPoints + points.getOne((long) -2).getCriteriaPoints());
							find = true;
							userCriteriaPoints.save(lucp.get(i));
						}
					}
					
					if(find == false){
						UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -2).getCriteriaPoints(), m.getCriteria(), user);
						userCriteriaPoints.save(ucp);
					}
					
					//#####################################################################################################################
					// SET TO THE OTHER PLAYER
					User otherPlayer = m.getFirstPlayer();
					UserPoint upFirst = otherPlayer.getUserGlobalPoints();
					Long otherPlayerGlobalPoints = upFirst.getUserPoints();
					upFirst.setUserPoints(otherPlayerGlobalPoints + points.getOne((long) -2).getGlobalPoints());
					
					find = false;
					List<UserCriteriaPoint> otherlucp = otherPlayer.getUserCriteriaPoints();
					for(int i=0; i< otherlucp.size(); i++)
					{
						if(otherlucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
						{
							Long firstPlayerCriteriaPoints = otherlucp.get(i).getPoints();
							otherlucp.get(i).setPoints(firstPlayerCriteriaPoints + points.getOne((long) -2).getCriteriaPoints());
							find = true;
							userCriteriaPoints.save(otherlucp.get(i));
						}
					}
					
					if(find == false){
						UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -2).getCriteriaPoints(), m.getCriteria(), otherPlayer);
						userCriteriaPoints.save(ucp);
						find = true;
					}
					
					//#####################################################################################################################
					
					// set the winner requirement
					m.setWinnerRequirement(r);
				}
				m.setFinish(true);
			}
		}
		moves.save(m);
	}
	
	// put, set the winner requirement from judge_view
	@RequestMapping(method = RequestMethod.PUT, value="/{moveJudgeId}/judgechooserequirement/{requirementId}")
	public void setJudgeChooseWinnerRequirement(Authentication authentication, @PathVariable Long moveJudgeId, @PathVariable Long requirementId) {
		
		DatabaseUser currentUser = (DatabaseUser) authentication.getPrincipal();
		User judge = users.findOne(currentUser.getUserId());
		
		JudgeMove jm = judgeMoves.findOne(moveJudgeId);
		Requirement r = requirements.findOne(requirementId);
		
		Move m = jm.getMove();
		
		// set the points for the judge
		UserPoint upJudge = judge.getUserGlobalPoints();

		Long judgeGlobalPoints = upJudge.getUserPoints();
		upJudge.setUserPoints(judgeGlobalPoints + points.getOne((long) -3).getGlobalPoints());
		userPoints.save(upJudge);
		
		// find the player that choose the winning requirement for set points
		if(m.getFirstPlayerChooseRequirement().getRequirementId() == requirementId){
			UserPoint upPlayer = m.getFirstPlayer().getUserGlobalPoints();
			Long playerGlobalPoints = upPlayer.getUserPoints();
			upPlayer.setUserPoints(playerGlobalPoints + points.getOne((long) -3).getGlobalPoints());
			userPoints.save(upPlayer);
		}else{
			UserPoint upPlayer = m.getSecondPlayer().getUserGlobalPoints();
			Long playerGlobalPoints = upPlayer.getUserPoints();
			upPlayer.setUserPoints(playerGlobalPoints + points.getOne((long) -3).getGlobalPoints());
			userPoints.save(upPlayer);
		}
				
		jm.setOpinionRequirement(r);
		jm.setGaveOpinion(true);
		jm.setJudge(judge);
		m.setWinnerRequirement(r);
		
		jm.setFinish(true);
		m.setFinish(true);
		
		moves.save(m);
		judgeMoves.save(jm);
	}
	
	// put, set the winner requirement from emit_view
	@RequestMapping(method = RequestMethod.PUT, value="/{moveJudgeId}/judgerequirement/{requirementId}")
	public void setJudgeWinnerRequirement(@PathVariable Long moveJudgeId, @PathVariable Long requirementId) {
		
		JudgeMove jm = judgeMoves.findOne(moveJudgeId);
		Requirement r = requirements.findOne(requirementId);		
		Move m = jm.getMove();
		
		
		// find the player that choose the winning requirement for set points 'winner argument' and 'loser argument'
		if(m.getFirstPlayerChooseRequirement().getRequirementId() == requirementId){
			UserPoint upPlayer = m.getFirstPlayer().getUserGlobalPoints();
			Long playerGlobalPoints = upPlayer.getUserPoints();
			upPlayer.setUserPoints(playerGlobalPoints + points.getOne((long) -4).getGlobalPoints());
			userPoints.save(upPlayer);
			
			boolean find = false;
			List<UserCriteriaPoint> firstlucp = m.getFirstPlayer().getUserCriteriaPoints();
			for(int i=0; i< firstlucp.size(); i++)
			{
				if(firstlucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
				{
					Long firstPlayerCriteriaPoints = firstlucp.get(i).getPoints();
					firstlucp.get(i).setPoints(firstPlayerCriteriaPoints + points.getOne((long) -4).getCriteriaPoints());
					find = true;
					userCriteriaPoints.save(firstlucp.get(i));
				}
			}
			
			if(find == false){
				UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -4).getCriteriaPoints(), m.getCriteria(), m.getFirstPlayer());
				userCriteriaPoints.save(ucp);
				find = true;
			}
			
			
			// loser get some criteria points
			find = false;
			List<UserCriteriaPoint> secondlucp = m.getSecondPlayer().getUserCriteriaPoints();
			for(int i=0; i< secondlucp.size(); i++)
			{
				if(secondlucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
				{
					Long secondPlayerCriteriaPoints = secondlucp.get(i).getPoints();
					secondlucp.get(i).setPoints(secondPlayerCriteriaPoints + points.getOne((long) -6).getCriteriaPoints());
					find = true;
					userCriteriaPoints.save(secondlucp.get(i));
				}
			}
			
			if(find == false){
				UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -6).getCriteriaPoints(), m.getCriteria(), m.getSecondPlayer());
				userCriteriaPoints.save(ucp);
				find = true;
			}
			// end loser player
		}else{
			UserPoint upPlayer = m.getSecondPlayer().getUserGlobalPoints();
			Long playerGlobalPoints = upPlayer.getUserPoints();
			upPlayer.setUserPoints(playerGlobalPoints + points.getOne((long) -4).getGlobalPoints());
			userPoints.save(upPlayer);
			
			boolean find = false;
			List<UserCriteriaPoint> secondlucp = m.getSecondPlayer().getUserCriteriaPoints();
			for(int i=0; i< secondlucp.size(); i++)
			{
				if(secondlucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
				{
					Long secondPlayerCriteriaPoints = secondlucp.get(i).getPoints();
					secondlucp.get(i).setPoints(secondPlayerCriteriaPoints + points.getOne((long) -4).getCriteriaPoints());
					find = true;
					userCriteriaPoints.save(secondlucp.get(i));
				}
			}
			
			if(find == false){
				UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -4).getCriteriaPoints(), m.getCriteria(), m.getSecondPlayer());
				userCriteriaPoints.save(ucp);
				find = true;
			}
			
			
			// loser get some criteria points
			find = false;
			List<UserCriteriaPoint> firstlucp = m.getFirstPlayer().getUserCriteriaPoints();
			for(int i=0; i< firstlucp.size(); i++)
			{
				if(firstlucp.get(i).getValutationCriteria().getCriteriaId() == m.getCriteria().getCriteriaId())
				{
					Long secondPlayerCriteriaPoints = firstlucp.get(i).getPoints();
					firstlucp.get(i).setPoints(secondPlayerCriteriaPoints + points.getOne((long) -6).getCriteriaPoints());
					find = true;
					userCriteriaPoints.save(firstlucp.get(i));
				}
			}
			
			if(find == false){
				UserCriteriaPoint ucp = new UserCriteriaPoint(points.getOne((long) -6).getCriteriaPoints(), m.getCriteria(), m.getFirstPlayer());
				userCriteriaPoints.save(ucp);
				find = true;
			}
			// end loser player			
		}
		
		m.setWinnerRequirement(r);		
		jm.setFinish(true);
		m.setFinish(true);
		
		moves.save(m);
		judgeMoves.save(jm);
	}	
}
