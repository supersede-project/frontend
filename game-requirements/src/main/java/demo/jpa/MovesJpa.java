package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Move;

public interface MovesJpa extends JpaRepository<Move, Long> {

}
