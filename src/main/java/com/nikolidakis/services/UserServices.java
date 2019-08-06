package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.UserException;
import com.nikolidakis.models.User;
import com.nikolidakis.requests.AuthenticateUserRequest;
import com.nikolidakis.requests.RegisterNewUserRequest;

import java.util.List;

public interface UserServices {

    List<User> getAllUser();

    String getToken(AuthenticateUserRequest request) throws AuthenticateException;

    void registerNewUser(RegisterNewUserRequest request) throws UserException;

    User findUserByToken(String token) throws AuthenticateException;

}
