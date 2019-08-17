package com.nikolidakis.repository.auctionrepository;

import com.nikolidakis.models.Auction;
import com.nikolidakis.models.ItemCategory;
import com.nikolidakis.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends CrudRepository<Auction, Long>, AuctionRepositoryCustom {

    List<Auction> findByCategories(ItemCategory category);

    List<Auction> findBySeller(User user);

    Optional<Auction> findById(Long id);

//   <Optional> Auction findById(Long id);

//    List<Auction> findByBids(List<Bid> bids);

}
