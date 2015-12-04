package demo.scheduled;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.jpa.JudgeMovesJpa;
import demo.jpa.MovesJpa;
import demo.jpa.NotificationsJpa;
import demo.jpa.ProfilesJpa;
import demo.model.JudgeMove;
import demo.model.Move;
import demo.model.Notification;
import demo.model.Profile;
import demo.model.User;
import eu.supersede.fe.multitenant.MultiJpaProvider;

@Component
public class RequirementScheduler {
	
	@Autowired
	MultiJpaProvider multiJpaProvider;
	
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
				Profile judge = multiJpaProvider.getRepository(ProfilesJpa.class, tenant).findByName("JUDGE");
				List<User> userJudges = judge.getUsers();
				
				NotificationsJpa notificationRepository = multiJpaProvider.getRepository(NotificationsJpa.class, tenant);
				JudgeMovesJpa judgeMovesRepository = multiJpaProvider.getRepository(JudgeMovesJpa.class, tenant);
				
				for(Move m : moves)
				{	
					if(m.getFirstPlayerChooseRequirement() != m.getSecondPlayerChooseRequirement())
					{
						for(User u : userJudges)
						{
							Notification n = new Notification("New conflict in move " + m.getMoveId(), u);
							notificationRepository.save(n);
						}
						
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
