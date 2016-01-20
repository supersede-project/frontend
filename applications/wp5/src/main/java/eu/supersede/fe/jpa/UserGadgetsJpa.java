package eu.supersede.fe.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.UserGadget;
import eu.supersede.fe.model.UserGadgetKey;

public interface UserGadgetsJpa extends JpaRepository<UserGadget, UserGadgetKey>{

	List<UserGadget> findByUserIdOrderByGadgetIdAsc(Long userId);
	
	Long deleteByUserId(Long userId);
	
}
