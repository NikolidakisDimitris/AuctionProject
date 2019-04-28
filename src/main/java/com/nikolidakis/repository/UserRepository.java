package com.nikolidakis.repository;

import com.nikolidakis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

//   User findByUsernameOrEmailIgnoreCase(String username);





}
