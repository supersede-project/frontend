package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.JudgeAct;

public interface JudgeActsJpa extends JpaRepository<JudgeAct, Long>{

}
