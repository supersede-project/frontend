package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.JudgeMove;
import demo.model.Move;

public interface JudgeMovesJpa extends JpaRepository<JudgeMove, Long> {

	JudgeMove findByMove(Move m);

}
