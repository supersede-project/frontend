package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Game;

public interface GamesJpa extends JpaRepository<Game, Long> {

}
