package com.nikolidakis.repository.auctionimagerepository;

import com.nikolidakis.models.AuctionImage;
import org.springframework.data.repository.CrudRepository;

public interface AuctionImageRepository extends CrudRepository<AuctionImage, Long>, AuctionImageRepositoryCustom {

}
