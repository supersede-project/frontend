package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.JudgeAct;
import demo.model.RequirementsMatrixData;

public interface JudgeActsJpa extends JpaRepository<JudgeAct, Long>{

	List<JudgeAct> findByRequirementsMatrixData(RequirementsMatrixData rmd);

}
