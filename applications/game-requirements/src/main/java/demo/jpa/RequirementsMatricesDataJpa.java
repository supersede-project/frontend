package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.RequirementsMatrixData;

public interface RequirementsMatricesDataJpa extends JpaRepository<RequirementsMatrixData, Long>{

}
