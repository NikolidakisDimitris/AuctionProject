package com.nikolidakis.services;

import com.nikolidakis.exceptions.*;
import com.nikolidakis.models.User;
import com.nikolidakis.requests.RegisterNewUserRequest;

import java.util.List;

public interface UserServices {

    List<User> getAllUser();

    User findUserById(Long id);

    String getToken(String username, String password) throws AuthenticateException;

    User registerNewUser(RegisterNewUserRequest request) throws UserException;

    User findUserByToken(String token) throws AuthenticateException;

    String rateUser(String userToken, Long userToBeRatedId, Long auctionId, int rate) throws AuthenticateException,
            AuctionException, BidException, UserException, RateException;

}
