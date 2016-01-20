package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.PlayerMove;
import demo.model.User;

public interface PlayerMovesJpa extends JpaRepository<PlayerMove, Long> {

	List<PlayerMove> findByPlayer(User player);
}
