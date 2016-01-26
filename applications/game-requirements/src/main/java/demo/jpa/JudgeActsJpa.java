package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.JudgeAct;
import demo.model.PlayerMove;
import demo.model.RequirementsMatrixData;

public interface JudgeActsJpa extends JpaRepository<JudgeAct, Long>{

	PlayerMove findByRequirementsMatrixData(RequirementsMatrixData requirementsMatrixData);

}
