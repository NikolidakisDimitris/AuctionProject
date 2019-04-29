package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.models.Auction;

import java.util.List;

public interface AuctionServices {

    List<Auction> getAllAuctions() throws AuctionException;
}
