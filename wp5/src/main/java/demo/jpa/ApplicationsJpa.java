package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Application;

public interface ApplicationsJpa extends JpaRepository<Application, Long> {

	List<Application> findByRequiredRole(String requiredRole);

}
