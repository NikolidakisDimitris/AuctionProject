package com.nikolidakis.repository;

import com.nikolidakis.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
