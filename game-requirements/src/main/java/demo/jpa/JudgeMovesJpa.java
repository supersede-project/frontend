package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.JudgeMove;
import demo.model.Move;

public interface JudgeMovesJpa extends JpaRepository<JudgeMove, Long> {

	JudgeMove findByMove(Move m);

	List<JudgeMove> findByFinishAndNotificationSent(boolean b, boolean c);

	List<JudgeMove> findByFinishAndNotificationSentAndFirstArgumentNotNullAndSecondArgumentNotNull(boolean b, boolean c);
}
