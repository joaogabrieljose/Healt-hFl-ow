package dev.joaogj.Auth.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import dev.joaogj.Auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<UserDetails> findUserEmail(String username);
}
