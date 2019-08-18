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

import java.time.LocalDate;
import java.util.ArrayList;
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

    //TODO: needs implementation.Has been implemented in the auction controller. Probably Will be removed.
    @Override
    public void newBid(NewBidRequest request) throws AuthenticateException, AuctionException {
        log.info(BID_SERVICES + NEW_BID + " ready to place a new bid");
        User bidder = userServices.findUserByToken(request.getBidderToken());

        Auction auction = auctionServices.getAuctionById(request.getAuctionId());
        Bid bid = new Bid(null, bidder, LocalDate.now().toString(), request.getBidderValue(), auction);

        bidRepository.save(bid);
        log.info(BID_SERVICES + NEW_BID + " ready to place a new bid");

    }

    @Override
    public List<Bid> getBidsByAuction(Long auctionId) throws AuctionException, BidException {
        List<Bid> bids = new ArrayList();
        Auction auction = auctionServices.getAuctionById(auctionId);
        if (isNull(auction)) {
            throw new AuctionException("No Auction found with this id");
        }

        bids = bidRepository.findByAuction(auction);
        return bids;
    }
}
