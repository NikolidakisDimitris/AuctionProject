package com.nikolidakis.repository.auctionrepository;

import com.nikolidakis.models.Auction;

import java.util.List;

public interface AuctionRepositoryCustom {

    public List<Auction> getOpenAuctions();

    public List<Auction> getClosedAuctions();

    public void deleteAuction(Auction auction);
}
