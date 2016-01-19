package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.RequirementsMatrix;

public interface RequirementsMatricesJpa extends JpaRepository<RequirementsMatrix, Long>{

}
