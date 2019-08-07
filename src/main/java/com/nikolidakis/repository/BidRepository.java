package com.nikolidakis.repository;

import com.nikolidakis.models.Bid;
import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {
}
