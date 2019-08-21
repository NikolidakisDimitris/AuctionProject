package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.requests.GetAuctionsByFieldRequest;
import com.nikolidakis.requests.NewAuctionRequest;
import com.nikolidakis.requests.NewBidRequest;

import java.util.List;

public interface AuctionServices {

    List<Auction> getAllAuctions() throws AuctionException;

    List<Auction> getOpenAuctions() throws AuctionException;

    List<Auction> getClosedAuctions() throws AuctionException;


    void newAuction(NewAuctionRequest request) throws AuctionException, AuthenticateException;

    Auction getAuctionById(Long auctionId) throws AuctionException;

    List<Auction> getAuctionsByField(GetAuctionsByFieldRequest request) throws AuthenticateException, AuctionException;

    void newBid(NewBidRequest request) throws AuthenticateException, AuctionException;

    void deleteAuctionById(Long auctionId, String token) throws AuthenticateException, AuctionException;

}
