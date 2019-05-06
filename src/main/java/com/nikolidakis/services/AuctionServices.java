package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.requests.NewAuctionRequest;

import java.util.List;

public interface AuctionServices {

    List<Auction> getAllAuctions() throws AuctionException;

    List<Auction> getOpenAuctions() throws AuctionException;

    void newAuction(NewAuctionRequest request) throws AuctionException, AuthenticateException;
}
