package com.example.computershopserver.repository;

import com.example.computershopserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String username);
    User findByResetPassToken(String token);
    Boolean existsByEmail(String email);
}
