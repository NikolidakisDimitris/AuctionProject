package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.UserException;
import com.nikolidakis.requests.AuthenticateUserRequest;
import com.nikolidakis.requests.RegisterNewUserRequest;
import com.nikolidakis.responses.AuthenticationResponse;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.UserServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.nikolidakis.models.constants.LogConstants.USER_CONTROLER;
import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;

@RestController
@RequestMapping("/users")
@Data
@Slf4j
public class UserController {

    @Autowired
    private UserServices services;

    //    Just for testing reasons
//    @RequestMapping("/allusers")
//    public Response getAllUsers() {
//        return new AllUsersResponse(SUCCESS, "All users found", services.getAllUsers());
//    }

    @PostMapping(value = "/registernewuser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response registerNewUser(@Valid @RequestBody RegisterNewUserRequest request) throws UserException {
        log.info(USER_CONTROLER + "Method registerNewUser");
        services.registerNewUser(request);
        log.info(USER_CONTROLER + "Successful registration of user ");
        return new Response(SUCCESS, "User Registered successfully");
    }

    @PostMapping(value = "/authenticateuser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response authenticateUser(@Valid @RequestBody AuthenticateUserRequest request) throws AuthenticateException {
        log.info(USER_CONTROLER + "Method getToken");
        String token = services.getToken(request);
        log.info(USER_CONTROLER + "Authentication was successful");
        return new AuthenticationResponse(SUCCESS, "Authenticated User OK", token);

    }

}
