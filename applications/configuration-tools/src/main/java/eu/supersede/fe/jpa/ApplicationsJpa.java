package eu.supersede.fe.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.supersede.fe.model.Application;

public interface ApplicationsJpa extends JpaRepository<Application, Long>
{
}