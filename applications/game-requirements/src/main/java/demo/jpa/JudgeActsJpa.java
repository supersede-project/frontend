package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import demo.model.Game;
import demo.model.JudgeAct;
import demo.model.RequirementsMatrixData;
import demo.model.ValutationCriteria;

public interface JudgeActsJpa extends JpaRepository<JudgeAct, Long>{

	List<JudgeAct> findByRequirementsMatrixData(RequirementsMatrixData rmd);

	@Query("SELECT ja FROM JudgeAct ja WHERE ja.requirementsMatrixData.game = ?1 AND ja.requirementsMatrixData.criteria = ?2")
	@Transactional
	List<JudgeAct> findByGameAndCriteria(Game game, ValutationCriteria criteria);
	
	@Query("SELECT ja FROM JudgeAct ja WHERE ja.requirementsMatrixData.game = ?1")
	@Transactional
	List<JudgeAct> findByGame(Game game);
	
	@Query("SELECT ja FROM JudgeAct ja WHERE ja.requirementsMatrixData.criteria = ?1")
	@Transactional
	List<JudgeAct> findByCriteria(ValutationCriteria criteria);
	
}
