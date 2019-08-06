package com.nikolidakis.repository;

import com.nikolidakis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll = null;

    boolean existsByUsername(String username);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findById(Long id);
}
