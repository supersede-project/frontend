package eu.supersede.fe.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.Application;

public interface ApplicationsJpa extends JpaRepository<Application, Long> {

	List<Application> findByRequiredProfileId(Long requiredProfileId);

	List<Application> findByRequiredProfileIdIn(List<Long> profIdList);

}
