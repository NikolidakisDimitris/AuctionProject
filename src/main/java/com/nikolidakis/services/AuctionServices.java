package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Bid;
import com.nikolidakis.requests.GetAuctionsByFieldRequest;
import com.nikolidakis.requests.NewAuctionRequest;
import com.nikolidakis.requests.NewBidRequest;

import java.util.List;

public interface AuctionServices {

    List<Auction> getAllAuctions() throws AuctionException;

    List<Auction> getOpenAuctions() throws AuctionException;

    List<Auction> getClosedAuctions() throws AuctionException;


    Auction newAuction(NewAuctionRequest request) throws AuctionException, AuthenticateException, ItemCategoryException;

    Auction getAuctionById(Long auctionId) throws AuctionException;

    List<Auction> getAuctionsByField(GetAuctionsByFieldRequest request) throws AuthenticateException, AuctionException;

    List<Auction> getAuctionsByField(String fieldName, String fieldValue) throws AuthenticateException,
            AuctionException;


    void newBid(NewBidRequest request) throws AuthenticateException, AuctionException;

    void deleteAuctionById(Long auctionId, String token) throws AuthenticateException, AuctionException;

    public Bid getHighestBid(Auction auction) throws BidException, AuctionException;


}
