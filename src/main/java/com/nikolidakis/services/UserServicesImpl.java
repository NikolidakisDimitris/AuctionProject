package com.nikolidakis.services;

import com.nikolidakis.exceptions.*;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Bid;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.UserRepository;
import com.nikolidakis.requests.RegisterNewUserRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static com.nikolidakis.utils.Utils.md5;
import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Data
@Slf4j
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final BidServices bidServices;

    @Autowired
    public UserServicesImpl(UserRepository userRepository, @Lazy BidServices bidServices) {
        this.userRepository = userRepository;
        this.bidServices = bidServices;
    }

    @Override
    public List<User> getAllUser() {

        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    //Register a new User
    @Override
    public User registerNewUser(RegisterNewUserRequest request) throws UserException {
        //search the DB for this username
        log.info(USER_SERVICE + REGISTER_NEW_USER + "Ready to call the DB to check is the username {} exists",
                request.getUsername());

        //Check if the username already exists, and not , only then save it, else throw exception
        boolean usernameExists = userRepository.existsByUsername(request.getUsername());

        if (usernameExists) {
            log.error(USER_SERVICE + REGISTER_NEW_USER + "The userName {} already exists", request.getUsername());
            throw new UserException("The name is already taken");
        }

        User user = new User(null, request.getUsername(), request.getPassword(), request.getFirstName(),
                request.getLastName(), request.getEmail(), request.getPhone(), request.getCountry(), request.getCity(),
                request.getAddress(), request.getAfm());

        //save to the DB the new user
        User newUser = userRepository.save(user);
        log.info(USER_SERVICE + REGISTER_NEW_USER + " User " + newUser.getUsername() + "saved successfully");
        return newUser;
    }

    //Find all the users
    @Override
    public String getToken(String username, String password) throws AuthenticateException {
        System.out.println(md5(password));

        log.info(USER_SERVICE + AUTHENTICATE_USER + "Ready to call the DB to authenticate the user");
        User user = userRepository.findByUsernameAndPassword(username, password);
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
        List<User> users = (List<User>) userRepository.findAll();
        System.out.println(token);

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

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public String rateUser(String userToken, User userToBeRated, Auction auction, int rate) throws AuthenticateException,
            UserException, RateException, BidException, AuctionException {
        //find the user by the token
        User currentUser = findUserByToken(userToken);

        //fetch the user from the database in order to be updatable
        System.out.println(userToBeRated.getId());
        User userToUpdate = userRepository.findById(userToBeRated.getId()).orElse(null);
        if (isEmpty(userToUpdate)) {
            throw new UserException("Empty user. The user to be rated is not correct. ");
        }

        //check if the auction is still open -> Open auctions can not be rated
        int year = Integer.parseInt(auction.getEndingTime().substring(0, 4));
        int month = Integer.parseInt(auction.getEndingTime().substring(5, 7));
        int day = Integer.parseInt(auction.getEndingTime().substring(8, 10));
        LocalDate date = LocalDate.of(year, month, day);

        int hour = Integer.parseInt(auction.getEndingTime().substring(11, 13));
        int mins = Integer.parseInt(auction.getEndingTime().substring(14));
        LocalTime time = LocalTime.of(hour, mins, 0, 0);

        LocalDateTime endingTime = LocalDateTime.of(date, time);

        LocalDateTime now = LocalDateTime.now();

        if (endingTime.isAfter(now)) {
            throw new RateException("The user can't be rated yet because the auction is still running");
        }

        //check if the userId is different from the auction seller -> If yes, then RateSeller ELSE RateBidder
        boolean rateSeller = true;
        String sellerOrBidderRated = null;
        if (currentUser.getId().equals(userToBeRated.getId())) {
            rateSeller = false;
        }
        Bid highestBid = bidServices.getHighestBid(auction.getId());
        User auctionWinner = highestBid.getBidder();

        //--------- RateSeller : if bidder is not the winner then he can not rate --------------------
        if (rateSeller) {
            // The Bidder is going to rate the seller
            //The one who is going to rate (the current user) has to be the winner of the auction
            if (auctionWinner.getId().equals(currentUser.getId())) {
                userToUpdate.setSellerRating(rate);
                Long votes = userToUpdate.getSellerRatingVotes() + 1;
                userToUpdate.setSellerRatingVotes(votes);
                userRepository.save(userToUpdate);
                sellerOrBidderRated = "seller";
            } else {
                throw new RateException("This user is not the winner so he can not rate the seller");
            }
        }
        //--------- RateBidder : if the bidder is not the winner, the seller can not rate him  ---------
        else {
            //The seller (the current user) is going to rate the winner of the auction (userToUpdate)
            //compare winner with the user to be rated. If they are NOT the same, then he is not the winner, so he
            // can not be rated
            if (auctionWinner.getId().equals(userToUpdate.getId())) {
                userToUpdate.setBidderRating(rate);
                Long votes = userToUpdate.getBidderRatingVotes();
                userToUpdate.setBidderRatingVotes(votes);
                userRepository.save(userToUpdate);
                sellerOrBidderRated = "bidder";
            } else {
                throw new RateException("The bidder can not be rated, because he is not the winner");
            }
        }
        return sellerOrBidderRated;

    }


}
