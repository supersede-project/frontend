package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.JudgeMove;

public interface JudgeMovesJpa extends JpaRepository<JudgeMove, Long> {

}
