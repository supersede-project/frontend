package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Game;
import demo.model.RequirementsMatrixData;

public interface RequirementsMatricesDataJpa extends JpaRepository<RequirementsMatrixData, Long>{

	List<RequirementsMatrixData> findByGame(Game g);

}
