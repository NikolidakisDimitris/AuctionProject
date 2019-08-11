package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Bid;
import com.nikolidakis.models.ItemCategory;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.BidRepository;
import com.nikolidakis.repository.ItemCategoryRepository;
import com.nikolidakis.repository.UserRepository;
import com.nikolidakis.repository.auctionrepository.AuctionRepository;
import com.nikolidakis.requests.GetAuctionRequest;
import com.nikolidakis.requests.GetAuctionsByFieldRequest;
import com.nikolidakis.requests.NewAuctionRequest;
import com.nikolidakis.requests.NewBidRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static java.util.Objects.isNull;

@Service
@Data
@Slf4j
public class AuctionServicesImpl implements AuctionServices {


    private AuctionRepository auctionRepository;
    private UserRepository userRepository;
    private UserServices userServices;
    private ItemCategoryRepository itemCategoryRepository;
    private BidRepository bidRepository;

    @Autowired
    private AuctionServicesImpl(AuctionRepository auctionRepository, UserRepository userRepository,
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
        List<Auction> auctions = (List<Auction>) auctionRepository.findAll();
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
    @Override
    public void newAuction(NewAuctionRequest request) throws AuctionException, AuthenticateException {
        log.info(AUCTION_SERVICES + NEW_AUCTION + " ready to open a new auction");

        //find the user who wants to open a new auction
        User user = userServices.findUserByToken(request.getToken());

        // SAVE ANY NEW CATEGORY
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            for (ItemCategory category : request.getCategories()) {
                if (category != null && category.getCategoryId() == null) {
                    category = itemCategoryRepository.save(category);
                }
            }
        }

        //create the auction item, in order to save it in the database
        Auction auction = new Auction(null, request.getNameOfItem(), user, request.getStartedTime(),
                request.getEndingTime(), request.getItemDescription(), request.getItemLocation(),
                request.getItemCountry(), null, request.getCategories());

        // save the new auction to the database
        log.info(auction.toString());
        Auction newAuction = auctionRepository.save(auction);
        log.info(AUCTION_SERVICES + NEW_AUCTION + " Auction registered successfully");
    }


    //TODO: needs bug fix
    @Override
    public Auction getAuctionById(GetAuctionRequest request) throws AuctionException {
        log.info(AUCTION_SERVICES + GET_AUCTION_BY_ID + "find auctions by ID");
        Auction auction = auctionRepository.findById(Long.parseLong(request.getAuctionId())).orElse(null);
        System.out.println(auction);
//        List<Auction> auctions = (List<Auction>) auctionRepository.findAll();

//        System.out.println("Ola ta Auction einai" + auctions);
//        System.out.println("To Auction ID" + request.getAuctionId());
//        for (Auction element : auctions) {
//            System.out.println("To element ID" + element.getId());
//            if (Long.parseLong(request.getAuctionId()) == (element.getId())) {
//                return element;
//            }
//        }
////        Auction auction = auctions.stream().map(Auction::getId).
//        System.out.println(auction);
//        log.info(AUCTION_SERVICES + GET_AUCTION_BY_ID + "auction found successfully ");
        return auction;
    }

    @Override
    public List<Auction> getAuctionsByField(GetAuctionsByFieldRequest request) throws AuthenticateException {
        log.info(AUCTION_SERVICES + GET_AUCTIONS_BY_FIELD + "find auctions by " + request.getFieldName());
        List<Auction> auctions = null;
        System.out.println(request.getFieldName());
        System.out.println(request.getFieldValue());
        switch (request.getFieldName()) {
            case "category":
                ItemCategory category = itemCategoryRepository.findById(Long.parseLong(request.getFieldValue())).orElse(null);
                auctions = auctionRepository.findByCategories(category);
                break;
            case "seller":
                User user = userRepository.findById(Long.parseLong(request.getFieldValue())).orElse(null);
                System.out.println(user);
                auctions = auctionRepository.findBySeller(user);
                break;

            //TODO: needs testing
            case "bidder":
                //find user by the provided token
                User user1 = userServices.findUserByToken(request.getFieldValue());
                System.out.println(user1);

                //find the bids given by this user
                List<Bid> bids = bidRepository.findByBidder(user1);

                //find the auctions by this bids
                auctions = auctionRepository.findByBids(bids);

                //TODO: needs bug fix
            case "auctionId":
                Auction auction = auctionRepository.findById(Long.parseLong(request.getFieldValue())).orElse(null);
                System.out.println("to auction einai" + auction);
                auctions = new ArrayList<>();
                auctions.add(auction);
                break;
        }
        return auctions;
    }

    @Override
    public void newBid(NewBidRequest request) throws AuthenticateException, AuctionException {
        log.info(AUCTION_SERVICES + NEW_BID + " ready to place a new bid");

        //find bidder by the provided token
        User user = userServices.findUserByToken(request.getBidderToken());

        // Create the new Bid object
        Bid bid = new Bid(null, user, LocalDateTime.now().toString(), request.getBidderValue());

        // Find the auction by the ID
        Auction auction = auctionRepository.findById(Long.parseLong(request.getAuctionId())).orElse(null);

        if (isNull(auction)) {
            throw new AuctionException("No such auction ");
        }
        //TODO: may have null pointer exception. Needs to check it
        //update the auction
        if (isNull(auction.getBids())) {
            List<Bid> bidList = new ArrayList<>();
            bidList.add(bid);
            auction.setBids(bidList);
        } else {
            auction.getBids().add(bid);
        }
        auctionRepository.save(auction);
    }
}
