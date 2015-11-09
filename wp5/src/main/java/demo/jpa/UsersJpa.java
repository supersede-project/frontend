package demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.User;

public interface UsersJpa extends JpaRepository<User, Long> {

	/*@Query("SELECT u FROM User u WHERE u.alias IS NOT NULL")
    List<User> findAliased();*/
	
	User findByEmail(String email);
	
}
