package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.ItemCategory;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.ItemCategoryRepository;
import com.nikolidakis.repository.UserRepository;
import com.nikolidakis.repository.auctionrepository.AuctionRepositoryImpl;
import com.nikolidakis.requests.NewAuctionRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nikolidakis.models.constants.LogConstants.*;
import static java.util.Objects.isNull;

@Service
@Data
@Slf4j
public class AuctionServicesImpl implements AuctionServices {


    private AuctionRepositoryImpl auctionRepository;
    private UserRepository userRepository;
    private UserServices userServices;
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private AuctionServicesImpl(AuctionRepositoryImpl auctionRepository, UserRepository userRepository,
                                UserServices userServices, ItemCategoryRepository itemCategoryRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.userServices = userServices;
        this.itemCategoryRepository = itemCategoryRepository;

    }


    //Method to get all the auctions
    @Override
    public List<Auction> getAllAuctions() throws AuctionException {
        log.info(AUCTION_SERVICES + GET_ALL_AUCTIONS + "find all auctions");
        List<Auction> auctions = auctionRepository.findAll();
        log.info(AUCTION_SERVICES + GET_ALL_AUCTIONS + "auctions found successfully ");
        return auctions;

    }

    //Method to get all the open auctions
    @Override
    public List<Auction> getOpenAuctions() throws AuctionException {
        log.info(AUCTION_SERVICES + GET_OPEN_AUCTIONS + "find all the open auctions");

        List<Auction> openAuctions = auctionRepository.getOpenAuctions();
        if (isNull(openAuctions)) {
            log.error(AUCTION_SERVICES + GET_OPEN_AUCTIONS + " Null list with auctions");
            throw new AuctionException("There are no auction open at the moment");
        }
        log.info(AUCTION_SERVICES + GET_OPEN_AUCTIONS + "auctions found successfully ");
        return openAuctions;
    }

    //Method to create a new auction
    public void newAuction(NewAuctionRequest request) throws AuctionException, AuthenticateException {
        log.info(AUCTION_SERVICES + NEW_AUCTION + " ready to open a new auction");

        //find the user who wants to open a new auction
        User user = userServices.findUserByToken(request.getToken());

        //If the user gives an categoryId for this auction for any reason apart from null -> AuctionException
//        if (!isNull(request.().getCategoryId())) {
//            log.info(AUCTION_SERVICES + NEW_AUCTION + "The categoryId should be specified by the database, wrong input ");
//            throw new AuctionException("Not allowed to give an categoryId for the auction.");
//        }

        //create the categories List
        Set<ItemCategory> categories = new HashSet<>();

        for (String currentCategory : request.getCategories()) {
            ItemCategory category = new ItemCategory(null, currentCategory, null);
        }


        //cretate the auction item
        Auction auction = new Auction(null, request.getNameOfItem(), user, request.getStartedTime(),
                request.getEndingTime(), request.getItemDescription(), request.getItemLocation(),
                request.getItemCountry(), null, categories);


        //create the new auction object, in order to save it in the database
//        Auction auction = new Auction(null, request.getNameOfItem(), categories, user, request.getStartedTime(),
//                request.getEndingTime(), request.getItemDescription(), request.getItemLocation(),
//                request.getItemCountry(), null);


        // save the new auction to the database
        Auction newAuction = auctionRepository.save(auction);
        log.info(AUCTION_SERVICES + NEW_AUCTION + " Auction registered successfully");


        //now need to save to the database the categories
//        for (ItemCategory currentCategory : categories) {
//            currentCategory.setCategoryId(auction.getId());
//            ItemCategory category = itemCategoryRepository.save(currentCategory);
//        }
    }

}
