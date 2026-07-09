package dev.joaogj.Auth.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import dev.joaogj.Auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<UserDetails> findUserByEmail(String username);
}
