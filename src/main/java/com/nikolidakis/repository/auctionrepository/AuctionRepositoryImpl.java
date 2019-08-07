package com.nikolidakis.repository.auctionrepository;

import com.nikolidakis.models.Auction;
import org.springframework.data.repository.CrudRepository;

public interface AuctionRepositoryImpl extends CrudRepository<Auction, Long>, AuctionRepositoryCustom {


}
