package com.nikolidakis.services;

import com.nikolidakis.models.User;
import com.nikolidakis.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class UserServicesImpl implements UserServices {

    @Autowired
   private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
//
//        List<User> users = new ArrayList<>();
//        userRepository.findAll().forEach(users::add);
//        return users;
    }

//    @Override
//    public User getUserByIdOrEmail(String userNameOrEmail) {
//        return userRepository.findByUsernameOrEmailIgnoreCase(userNameOrEmail);
//    }
}
