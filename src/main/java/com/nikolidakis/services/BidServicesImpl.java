package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Bid;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.BidRepository;
import com.nikolidakis.requests.NewBidRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static java.util.Objects.isNull;

@Service
@Data
@Slf4j
public class BidServicesImpl implements BidServices {

    @Autowired
    private final BidRepository bidRepository;
    @Autowired
    private final UserServices userServices;
    @Autowired
    private final AuctionServices auctionServices;

    public List<Bid> getBids() {
        log.info(BID_SERVICES + GET_ALL_BIDS + " ready to get all the bids");
        return (List<Bid>) bidRepository.findAll();

    }

    @Override
    public void newBid(NewBidRequest request) throws AuthenticateException, AuctionException, BidException {
        log.info(BID_SERVICES + NEW_BID + " ready to place a new bid");
        User bidder = userServices.findUserByToken(request.getBidderToken());

        Auction auction = auctionServices.getAuctionById(request.getAuctionId());
        if (isNull(auction) || isNull(auction.getId())) {
            throw new AuctionException("This auction doesn't exist");
        }

        //Get the bids for this auction and check if there is any higher bid
        List<Bid> bids = getBidsByAuction(auction.getId());
        if (!isNull(bids)) {
            for (Bid current : bids) {
                if (request.getBidderValue() <= current.getBidPrice()) {
                    throw new BidException("The bid is not valid because it is not the highest bid");
                }
            }
        }

        Bid bid = new Bid(null, bidder, LocalDateTime.now().toString(), request.getBidderValue(), auction);

        bidRepository.save(bid);
        log.info(BID_SERVICES + NEW_BID + " ready to place a new bid");

    }

    @Override
    public List<Bid> getBidsByAuction(Long auctionId) throws AuctionException, BidException {
        log.info(BID_SERVICES + GET_BIDS_BY_AUCTION_ID + " ready to find the bids according to the auction Id ");
        List<Bid> bids = new ArrayList();
        Auction auction = auctionServices.getAuctionById(auctionId);
        if (isNull(auction)) {
            throw new AuctionException("No Auction found with this id");
        }

        bids = bidRepository.findByAuction(auction);
        log.info(BID_SERVICES + GET_BIDS_BY_AUCTION_ID + " Bids found successfully. Returning bids list ");
        return bids;
    }

    @Override
    public Bid getHighestBid(long auctionId) throws BidException, AuctionException {
        log.info(BID_SERVICES + GET_HIGHEST_BID_BY_AUCTION + " Ready to fing the highest Bid . ");
        List<Bid> auctions = getBidsByAuction(auctionId);
        Bid highestBid = null;
        if (!isNull(auctions)) {
            highestBid = auctions.stream().max(Comparator.comparingDouble(Bid::getBidPrice)).get();
        }
        log.info(BID_SERVICES + GET_HIGHEST_BID_BY_AUCTION + " Highest Bid found successfully. Returning the bid");
        return highestBid;
    }

}
