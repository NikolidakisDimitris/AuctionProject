package com.nikolidakis.repository.auctionimagerepository;

import com.nikolidakis.models.AuctionImage;

import java.util.List;

public interface AuctionImageRepositoryCustom {


    List<AuctionImage> findAllImagePathsForAuctionId(long auctionId);

}
