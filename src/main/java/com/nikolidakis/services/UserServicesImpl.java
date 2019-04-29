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

    //Find all the users
    private List<User> getAllUsers() {
        return repository.findAll();
    }

    //Register a new User
    @Override
    public void registerNewUser(RegisterNewUserRequest request) throws UserException {
        //search the DB for this username
        log.info(USER_SERVICE + REGISTER_NEW_USER + "Ready to call the DB to check is the username {} exists",
                request.getUser().getUsername());
        User user = repository.findByUsername(request.getUser().getUsername());

        //If there is already a user with this username ->UserException
        if (!isNull(user)) {
            log.error(USER_SERVICE + REGISTER_NEW_USER + "The userName already exists");
            throw new UserException("This name is already taken");
        }

        //If the user gives an id for any reason apart from null -> UserException
        if (!isNull(request.getUser().getId())) {
            log.error(USER_SERVICE + REGISTER_NEW_USER + "The id is specified by the database, wrong input");
            throw new UserException("You Should not give the id");
        }
        User registerNewUser = repository.save(request.getUser());
        log.info(USER_SERVICE + REGISTER_NEW_USER + "saved successfully");
    }

    //Find all the users
    @Override
    public String getToken(AuthenticateUserRequest request) throws AuthenticateException {

        log.info(USER_SERVICE + AUTHENTICATE_USER + "Ready to call the DB to authenticate the user");
        User user = repository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
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
        List<User> users = getAllUsers();

        for (User currentUser : users) {
            String userToken = md5(currentUser.getUsername() + currentUser.getPassword());
            if (token.equals(userToken)) {
                log.info(USER_SERVICE + "Method findUserByToken > Token is ok ! The user is :" + currentUser.getUsername());
                return currentUser;
            }
        }

        log.error(USER_SERVICE + "Method findUserByToken > Wrong token");
        throw new AuthenticateException("Incorrect token !");
    }


}
