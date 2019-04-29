package com.nikolidakis.repository;

import com.nikolidakis.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
