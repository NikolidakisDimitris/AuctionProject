package com.nikolidakis.repository;

import com.nikolidakis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//   public User findByUsernameOrEmailIgnoreCase(String username);





}
