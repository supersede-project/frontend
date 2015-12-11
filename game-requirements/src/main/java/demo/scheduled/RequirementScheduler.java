package demo.scheduled;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import demo.jpa.JudgeMovesJpa;
import demo.jpa.MovesJpa;
import demo.model.JudgeMove;
import demo.model.Move;
import eu.supersede.fe.multitenant.MultiJpaProvider;
import eu.supersede.fe.notification.NotificationUtil;

@Component
public class RequirementScheduler {
	
	@Autowired
	MultiJpaProvider multiJpaProvider;
	
	@Autowired
    private NotificationUtil notificationUtil;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Scheduled(fixedRate = 10000)
	@Transactional
	private void notifyJudgesForArguments()
	{
		log.debug("partito notifyJudgesForArguments");

		Map<String, JudgeMovesJpa> judgeMoveRepositories = multiJpaProvider.getRepositories(JudgeMovesJpa.class);
		
		for(String tenant : judgeMoveRepositories.keySet())
		{
			JudgeMovesJpa judgeMoveRepository = judgeMoveRepositories.get(tenant);
			List<JudgeMove> judgeMoves = judgeMoveRepository.findByFinishAndNotificationSent(true, false);
			//List<JudgeMove> judgeMoves = judgeMoveRepository.findByFinishAndNotificationSentAndFirstArgumentNotNullAndSecondArgumentNotNull(true, false);
			log.debug("SIZE: " + judgeMoves.size());
								
			for(JudgeMove jm : judgeMoves)
			{			
				log.debug("MOVE ID: " + jm.getJudgeMoveId());
				log.debug("FIRST ARG: " + jm.getFirstArgument());
				log.debug("SECOND ARG: " + jm.getSecondArgument());

				if((jm.getFirstArgument() != null) && (jm.getSecondArgument() != null))
				{		
					notificationUtil.createNotificationsForProfile(tenant, "JUDGE", "There are two arguments in move " + jm.getJudgeMoveId(), "game-requirements/judge_moves");				
					jm.setToJudge(false);
					jm.setToSolve(true);
					jm.setNotificationSent(true);
					jm.setFinish(false);
					judgeMoveRepository.save(jm);
				}
			}		
		}		
	}
	
	@Scheduled(fixedRate = 10000)
	private void notifyJudgesForConflicts()
	{
		log.debug("partito notifyJudgesForConflicts");

		Map<String, MovesJpa> moveRepositories = multiJpaProvider.getRepositories(MovesJpa.class);
		
		for(String tenant : moveRepositories.keySet())
		{
			MovesJpa moveRepository = moveRepositories.get(tenant);
			List<Move> moves = moveRepository.findByFinishAndNotificationSent(true, false);
			
			if(moves.size() > 0)
			{				
				JudgeMovesJpa judgeMovesRepository = multiJpaProvider.getRepository(JudgeMovesJpa.class, tenant);
				
				for(Move m : moves)
				{	
					if(m.getFirstPlayerChooseRequirement() != m.getSecondPlayerChooseRequirement())
					{												
						JudgeMove jm = new JudgeMove(m);
						jm.setToJudge(true);
						
						judgeMovesRepository.save(jm);
						
						notificationUtil.createNotificationsForProfile(tenant, "JUDGE", "New conflict in move " + m.getMoveId(), "game-requirements/judge_moves");				
						
						m.setNotificationSent(true);
						moveRepository.save(m);
					}
				}		
			}	
		}		
	}	
}