package com.nikolidakis.services;

import com.nikolidakis.models.User;

import java.util.List;

public interface UserServices {

//    User getUserByIdOrEmail(String userNameOrEmail);

    List<User> getAllUsers();

}
