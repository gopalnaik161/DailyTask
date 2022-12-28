package Tesqurelexam.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Tesqurelexam.app.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByIsActive(String  isActive);
	Optional<User> findByUserIdAndIsActive(long userId, String isActive);
	
	
	
}

