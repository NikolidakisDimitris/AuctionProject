package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.UserException;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.UserRepository;
import com.nikolidakis.requests.AuthenticateUserRequest;
import com.nikolidakis.requests.RegisterNewUserRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static com.nikolidakis.utils.Utils.md5;
import static java.util.Objects.isNull;

@Service
@Data
@Slf4j
public class UserServicesImpl implements UserServices {

    @Autowired
    private final UserRepository repository;

    @Override
    public List<User> getAllUser() {

        List<User> users = (List<User>) repository.findAll();
        return users;
    }


    //Register a new User
    @Override
    public void registerNewUser(RegisterNewUserRequest request) throws UserException {
        //search the DB for this username
        log.info(USER_SERVICE + REGISTER_NEW_USER + "Ready to call the DB to check is the username {} exists",
                request.getUsername());

        //Check if the username already exists, and not , only then save it, else throw exception
        boolean usernameExists = repository.existsByUsername(request.getUsername());

        if (usernameExists) {
            log.error(USER_SERVICE + REGISTER_NEW_USER + "The userName {} already exists", request.getUsername());
            throw new UserException("The name is already taken");
        }

        User user = new User(null, request.getUsername(), request.getPassword(), request.getFirstName(),
                request.getLastName(), request.getEmail(), request.getPhone(), request.getCountry(), request.getCity(),
                request.getAddress(), request.getAfm());

        //save to the DB the new user
        User registerNewUser = repository.save(user);
        log.info(USER_SERVICE + REGISTER_NEW_USER + " User " + registerNewUser.getUsername() + "saved successfully");
    }

    //Find all the users
    @Override
    public String getToken(AuthenticateUserRequest request) throws AuthenticateException {
        System.out.println(md5(request.getPassword()));

        log.info(USER_SERVICE + AUTHENTICATE_USER + "Ready to call the DB to authenticate the user");
        User user = repository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        System.out.println(user);
        if (isNull(user)) {
            log.error(USER_SERVICE + AUTHENTICATE_USER + "There is no such user");
            throw new AuthenticateException("Not Authenticated User");
        }

        log.info(USER_SERVICE + AUTHENTICATE_USER + "Authentication was successful ");

        //i create the token from the concatenation of the username and password
        String token = md5(user.getUsername() + user.getPassword());
        return token;
    }

    @Override
    public User findUserByToken(String token) throws AuthenticateException {
        List<User> users = (List<User>) repository.findAll();

        if (isNull(token)) {
            log.error(USER_SERVICE + FIND_USER_BY_TOKEN + "null token");
            throw new AuthenticateException("The token is null");
        }

        for (User currentUser : users) {
            String currentUserToken = md5(currentUser.getUsername() + currentUser.getPassword());

            if (token.equals(currentUserToken)) {
                log.info(USER_SERVICE + FIND_USER_BY_TOKEN + "Token is ok ! The user is :" + currentUser.getUsername());
                return currentUser;
            }
        }

        log.error(USER_SERVICE + FIND_USER_BY_TOKEN + "Wrong token");
        throw new AuthenticateException("Incorrect token !");
    }
}
