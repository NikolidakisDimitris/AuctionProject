package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.models.Bid;
import com.nikolidakis.requests.NewBidRequest;

import java.util.List;

public interface BidServices {
    public List<Bid> getBids();

    void newBid(NewBidRequest request) throws AuthenticateException, AuctionException, BidException;

    List<Bid> getBidsByAuction(Long auctionId) throws AuctionException, BidException;

    Bid getHighestBid(long auctionId) throws BidException, AuctionException;
}
