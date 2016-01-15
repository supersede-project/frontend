package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.GameRequirement;

public interface GameRequirementsJpa extends JpaRepository<GameRequirement, Long> {

}
