package demo.scheduled;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	
	@Scheduled(fixedRate = 10000)
	private void notifyJudges()
	{
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
						notificationUtil.createNotificationsForProfile(tenant, "JUDGE", "New conflict in move " + m.getMoveId());				
						
						JudgeMove jm = new JudgeMove(m);
						judgeMovesRepository.save(jm);
						
						m.setNotificationSent(true);
						moveRepository.save(m);
					}
				}		
			}	
		}		
	}
}
