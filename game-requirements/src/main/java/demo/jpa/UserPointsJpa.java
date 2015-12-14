package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.UserPoint;

public interface UserPointsJpa extends JpaRepository<UserPoint, Long>{

}
