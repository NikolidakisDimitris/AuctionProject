package com.nikolidakis.repository.auctionrepository;

import com.nikolidakis.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepositoryImpl extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {


}
