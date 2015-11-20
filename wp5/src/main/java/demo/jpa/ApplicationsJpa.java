package demo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Application;

public interface ApplicationsJpa extends JpaRepository<Application, Long> {

	List<Application> findByRequiredProfileId(Long requiredProfileId);

}
