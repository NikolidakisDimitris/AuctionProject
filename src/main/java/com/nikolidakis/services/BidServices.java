package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Bid;
import com.nikolidakis.requests.NewBidRequest;

import java.util.List;

public interface BidServices {
    public List<Bid> getBids();

    void newBid(NewBidRequest request) throws AuthenticateException;

}
