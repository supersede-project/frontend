package demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.jpa.JudgeMovesJpa;
import demo.model.JudgeMove;
import eu.supersede.fe.exception.NotFoundException;
import eu.supersede.fe.notification.NotificationUtil;

@RestController
@RequestMapping("/judgemove")
public class JudgeMoveRest {

	@Autowired
    private JudgeMovesJpa judgeMoves;
	
	@Autowired
    private NotificationUtil notificationUtil;
	
	// get all the judgeMoves if user is a judge
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(value = "",  method = RequestMethod.GET)
	public List<JudgeMove> getJudgeMoves() {

		return judgeMoves.findAll();
	}
		
	// get a specific judgeMove if user is a judge
	@PreAuthorize("hasRole('JUDGE')")
	@RequestMapping(value = "/{judgeMoveId}",  method = RequestMethod.GET)
	public JudgeMove getJudgeMove(@PathVariable Long judgeMoveId) {

		JudgeMove jm = judgeMoves.findOne(judgeMoveId);
		if(jm == null)
		{
			throw new NotFoundException();
		}
		
		return jm;
	}
	
	// put, for send notification to the player that the judge wants arguments from them
	@RequestMapping(method = RequestMethod.PUT, value="/{judgeMoveId}/needarguments")
	public void needArguments(Authentication authentication, @PathVariable Long judgeMoveId) {
				
		JudgeMove jm = judgeMoves.findOne(judgeMoveId);		
		jm.setNeedArguments(true);
		jm.setFinish(true);
		
		notificationUtil.createNotificationForUser(jm.getMove().getFirstPlayer().getUserId(), "Need argument for move " + jm.getMove().getMoveId(), "game-requirements/argue_view?moveId=" + jm.getMove().getMoveId());
		notificationUtil.createNotificationForUser(jm.getMove().getSecondPlayer().getUserId(), "Need argument for move " + jm.getMove().getMoveId(), "game-requirements/argue_view?moveId=" + jm.getMove().getMoveId());
		
		judgeMoves.save(jm);
	}
}
