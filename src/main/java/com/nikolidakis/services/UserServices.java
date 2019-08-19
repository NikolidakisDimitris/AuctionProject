package com.nikolidakis.services;

import com.nikolidakis.exceptions.*;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.User;
import com.nikolidakis.requests.AuthenticateUserRequest;
import com.nikolidakis.requests.RegisterNewUserRequest;

import java.util.List;

public interface UserServices {

    List<User> getAllUser();

    User findUserById(Long id);

    String getToken(AuthenticateUserRequest request) throws AuthenticateException;

    void registerNewUser(RegisterNewUserRequest request) throws UserException;

    User findUserByToken(String token) throws AuthenticateException;

    String rateUser(String userToken, User userToBeRated, Auction auction, int rate) throws AuthenticateException,
            AuctionException, BidException, UserException, RateException;

}
