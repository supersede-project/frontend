package demo.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.jpa.MovesJpa;
import demo.model.Move;
import eu.supersede.fe.multitenant.MultiJpaProvider;

@Component
public class RequirementScheduler {
	
	@Autowired
	MultiJpaProvider multiJpaProvider;
	
	@Scheduled(fixedRate = 10000)
	private void notifyJudges()
	{
		List<MovesJpa> moveRepositories = multiJpaProvider.getRepositories(MovesJpa.class);
		
		for(MovesJpa moveRepository : moveRepositories)
		{
			List<Move> moves = moveRepository.findByFinishAndNotificationSent(true, false);
		}
		
		
		
	}
}
