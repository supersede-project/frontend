package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Move;
import demo.model.User;

public interface MovesJpa extends JpaRepository<Move, Long> {

	List<Move> findByFirstPlayerOrSecondPlayer(User user, User user2);

}
