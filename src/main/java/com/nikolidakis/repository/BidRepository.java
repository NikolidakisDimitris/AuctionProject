package com.nikolidakis.repository;

import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Bid;
import com.nikolidakis.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BidRepository extends CrudRepository<Bid, Long> {
    List<Bid> findByBidder(User user);

    List<Bid> findByAuction(Auction auction);


}
