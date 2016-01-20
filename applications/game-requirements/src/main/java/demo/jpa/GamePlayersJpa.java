package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.GamePlayer;

public interface GamePlayersJpa extends JpaRepository<GamePlayer, Long>  {
	
}
