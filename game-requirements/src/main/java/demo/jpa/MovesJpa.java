package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import demo.model.Move;
import demo.model.User;

public interface MovesJpa extends JpaRepository<Move, Long> {

	List<Move> findByFirstPlayerOrSecondPlayer(User user, User user2);
	
	//@Query("SELECT DISTINCT user FROM UserCriteriaPoint ucp WHERE ucp.valutationCriteria = ?1")
	
	@Query("SELECT moves FROM Move moves WHERE ((moves.firstPlayer = ?1 AND moves.firstPlayerChooseRequirement IS NULL) OR (moves.secondPlayer =?1 AND moves.secondPlayerChooseRequirement IS NULL))")
	List<Move> findSpecial(User user);

	List<Move> findByFinishAndNotificationSent(boolean b, boolean c);

}
