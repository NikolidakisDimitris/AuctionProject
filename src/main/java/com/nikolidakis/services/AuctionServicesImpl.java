package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Bid;
import com.nikolidakis.models.ItemCategory;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.BidRepository;
import com.nikolidakis.repository.ItemCategoryRepository;
import com.nikolidakis.repository.UserRepository;
import com.nikolidakis.repository.auctionrepository.AuctionRepository;
import com.nikolidakis.requests.GetAuctionsByFieldRequest;
import com.nikolidakis.requests.NewAuctionRequest;
import com.nikolidakis.requests.NewBidRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.nikolidakis.models.constants.LogConstants.*;
import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Data
@Slf4j
public class AuctionServicesImpl implements AuctionServices {


    private AuctionRepository auctionRepository;
    private UserRepository userRepository;
    private UserServices userServices;
    private ItemCategoryRepository itemCategoryRepository;
    private BidRepository bidRepository;
    private ImageServices imageServices;

    @Autowired
    private AuctionServicesImpl(AuctionRepository auctionRepository, UserRepository userRepository,
                                UserServices userServices, ItemCategoryRepository itemCategoryRepository,
                                BidRepository bidRepository, ImageServices imageServices) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.userServices = userServices;
        this.itemCategoryRepository = itemCategoryRepository;
        this.bidRepository = bidRepository;
        this.imageServices = imageServices;

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


    //Method to get all the closed auctions
    @Override
    public List<Auction> getClosedAuctions() throws AuctionException {
        log.info(AUCTION_SERVICES + GET_CLOSED_AUCTIONS + "find all the closed auctions");

        List<Auction> openAuctions = auctionRepository.getOpenAuctions();
        if (isNull(openAuctions)) {
            log.error(AUCTION_SERVICES + GET_CLOSED_AUCTIONS + " Null list with auctions");
            throw new AuctionException("There are no closed auctions at the moment");
        }
        log.info(AUCTION_SERVICES + GET_CLOSED_AUCTIONS + "auctions found successfully ");
        return openAuctions;
    }


    //Method to create a new auction
    @Override
    public Auction newAuction(NewAuctionRequest request) throws AuctionException, AuthenticateException,
            ItemCategoryException {
        log.info(AUCTION_SERVICES + NEW_AUCTION + " ready to open a new auction");

        //find the user who wants to open a new auction
        User user = userServices.findUserByToken(request.getToken());

        //check if any of the categories doesn't exist. If even one is missing then throw exception
        List<ItemCategory> dbCatedories = (List<ItemCategory>) itemCategoryRepository.findAll();
        for (ItemCategory current : request.getCategories()) {
            if (!dbCatedories.contains(current)) {
                throw new ItemCategoryException("Category : \"" + current.getCategoryName() + "\" doesn't exist");
            }
        }

        //create the auction item, in order to save it in the database
        Auction auction = new Auction(null, request.getNameOfItem(), user, request.getStartedTime(),
                request.getEndingTime(), request.getItemDescription(), user.getCity(),
                user.getCountry(), request.getInitialPrice(), request.getCategories());

        // save the new auction to the database
        log.info(auction.toString());
        Auction newAuction = auctionRepository.save(auction);
        log.info(AUCTION_SERVICES + NEW_AUCTION + " Auction registered successfully");
        return newAuction;
    }


    @Override
    public Auction getAuctionById(Long auctionId) throws AuctionException {
        log.info(AUCTION_SERVICES + GET_AUCTION_BY_ID + "find auctions by ID");
        Auction auction = auctionRepository.findById(auctionId).orElse(null);
        if (!isNull(auction)) {
            log.info(AUCTION_SERVICES + GET_AUCTION_BY_ID + "auction found successfully ");
            return auction;
        } else {
            log.info(AUCTION_SERVICES + GET_AUCTION_BY_ID + "auction is null. No auction with this id");
            throw new AuctionException("There is no auction with this id");
        }
    }

    @Override
    public List<Auction> getAuctionsByField(GetAuctionsByFieldRequest request) throws AuthenticateException, AuctionException {
        log.info(AUCTION_SERVICES + GET_AUCTIONS_BY_FIELD + "find auctions by " + request.getFieldName());
        List<Auction> auctions = new ArrayList<>();
        switch (request.getFieldName().toLowerCase()) {
            case "categoryid":
                ItemCategory category = itemCategoryRepository.findById(Long.parseLong(request.getFieldValue())).orElse(null);
                auctions = auctionRepository.findByCategories(category);
                break;
            case "sellerid":
                User user = userRepository.findById(Long.parseLong(request.getFieldValue())).orElse(null);
                auctions = auctionRepository.findBySeller(user);
                break;
            case "sellertoken":
                User user2 = userServices.findUserByToken(request.getFieldValue());
                auctions = auctionRepository.findBySeller(user2);
                break;
            case "biddertoken":
                //find user by the provided token
                User user1 = userServices.findUserByToken(request.getFieldValue());

                //find the bids given by this user
                List<Bid> bids = bidRepository.findByBidder(user1);

                //get each auction that this user has given a bid (use set to take each auction only once, and then
                // move set to the list
                Set<Auction> auctionSet = new HashSet<>();
                for (Bid current : bids) {
                    auctionSet.add(current.getAuction());
                }
                auctions.addAll(auctionSet);
                break;
            default:
                throw new AuctionException("Wrong field name");
        }
        return auctions;
    }

    @Override
    public List<Auction> getAuctionsByField(String fieldName, String fieldValue) throws AuthenticateException, AuctionException {
        log.info(AUCTION_SERVICES + GET_AUCTIONS_BY_FIELD + "find auctions by " + fieldName);
        List<Auction> auctions = new ArrayList<>();

        switch (fieldName) {
            case "categoryId":
                ItemCategory category = itemCategoryRepository.findById(Long.parseLong(fieldValue)).orElse(null);
                auctions = auctionRepository.findByCategories(category);
                break;
            case "sellerId":
                User user = userRepository.findById(Long.parseLong(fieldValue)).orElse(null);
                auctions = auctionRepository.findBySeller(user);
                break;
            default:
                throw new AuctionException("Wrong field name");
        }
        return auctions;
    }

    @Override
    public void newBid(NewBidRequest request) throws AuthenticateException, AuctionException {
        log.info(AUCTION_SERVICES + NEW_BID + " ready to place a new bid");

        //find bidder by the provided token
        User user = userServices.findUserByToken(request.getBidderToken());

        // Find the auction by the ID
        Auction auction = auctionRepository.findById(request.getAuctionId()).orElse(null);

        if (isNull(auction)) {
            throw new AuctionException("No such auction ");
        }
        // Create the new Bid object
        Bid bid = new Bid(null, user, LocalDateTime.now().toString(), request.getBidderValue(), auction);

        auctionRepository.save(auction);
    }

    @Override
    public void deleteAuctionById(Long auctionId, String token) throws AuthenticateException, AuctionException {
        //find the user
        log.info(AUCTION_SERVICES + DELETE_AUCTION_BY_ID + " Ready to find the user who want to delete the auction");
        User user = userServices.findUserByToken(token);

        //find the auction
        Auction auction = auctionRepository.findById(auctionId).orElse(null);

        if (isEmpty(auction)) {
            log.info(AUCTION_SERVICES + DELETE_AUCTION_BY_ID + " The auction doesn't exist ");
            throw new AuctionException("The auction doesn't exist (auction is null)");
        }

        //check if the seller is the user who want to delete the auction
        if (!user.equals(auction.getSeller())) {
            log.info(AUCTION_SERVICES + DELETE_AUCTION_BY_ID + " User is different than the seller ");
            throw new AuctionException("The auction can not be deleted as the user is not the owner of the auction ");
        }

        //Check if this auction has bids
        List<Bid> bids = bidRepository.findByAuction(auction);
        if (!isEmpty(bids)) {
            log.info(AUCTION_SERVICES + DELETE_AUCTION_BY_ID + " Bids exist, so delete can not proceed");
            throw new AuctionException("The auction can not be deleted because this auction has already bids");
        }

        //delete the auction if bids is empty
        log.info(AUCTION_SERVICES + DELETE_AUCTION_BY_ID + " Ready to delete the auction ");
        auction.getCategories().clear();
        auctionRepository.deleteAuction(auction);
        log.info(AUCTION_SERVICES + DELETE_AUCTION_BY_ID + " The auction has been deleted successfully ");

    }

    @Override
    public Bid getHighestBid(Auction auction) throws BidException, AuctionException {
        log.info(AUCTION_SERVICES + GET_HIGHEST_BID_BY_AUCTION + " Ready to find the highest Bid . ");
        Set<Bid> bids = auction.getBids();
        Bid highestBid = null;
        if (isNull(bids)) {
            log.info(AUCTION_SERVICES + GET_HIGHEST_BID_BY_AUCTION + "There are no bids for  auction with id : " + auction.getId());
            throw new BidException("There is no bids for or  auction with id : " + auction.getId());
        }
        highestBid = bids.stream().max(Comparator.comparingDouble(Bid::getBidPrice)).get();

        log.info(AUCTION_SERVICES + GET_HIGHEST_BID_BY_AUCTION + " Highest Bid found successfully. Returning the bid");
        return highestBid;
    }


}
