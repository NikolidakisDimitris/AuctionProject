package com.nikolidakis.repository;

import com.nikolidakis.models.Auction;

import java.util.List;

public interface AuctionRepositoryCustom {

    public List<Auction> getOpenAuctions();
}
