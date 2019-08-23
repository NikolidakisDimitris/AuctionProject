package com.nikolidakis.repository.auctionimagerepository;

import com.nikolidakis.models.AuctionImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.nikolidakis.models.constants.DataBaseConstants.DATABASE;
import static com.nikolidakis.models.constants.LogConstants.AUCTION_IMAGE_REPOSITORY_CUSTOM_IMPL;
import static com.nikolidakis.models.constants.LogConstants.FIND_ALL_IMAGE_PATHS_FOR_AUCTION_ID;

@Repository
@Transactional
@Slf4j
public class AuctionImageRepositoryCustomImpl implements AuctionImageRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AuctionImage> findAllImagePathsForAuctionId(long auctionId) {

        List<AuctionImage> auctionImages = null;
        log.info(AUCTION_IMAGE_REPOSITORY_CUSTOM_IMPL + FIND_ALL_IMAGE_PATHS_FOR_AUCTION_ID + "prepared to call the " +
                "Database to get the AuctionImages");
        try {
            TypedQuery<AuctionImage> query =
                    (TypedQuery<AuctionImage>) entityManager.createNativeQuery("SELECT * FROM " + DATABASE + ".images \n" +
                            "where image_name \n" +
                            "like \"auction_" + auctionId + "_%\"", AuctionImage.class);
            auctionImages = query.getResultList();
            log.info(AUCTION_IMAGE_REPOSITORY_CUSTOM_IMPL + FIND_ALL_IMAGE_PATHS_FOR_AUCTION_ID + " Database call and" +
                    " cast of objects to type AuctionImage was successful ");
        } catch (Exception e) {
            log.error(AUCTION_IMAGE_REPOSITORY_CUSTOM_IMPL + FIND_ALL_IMAGE_PATHS_FOR_AUCTION_ID + "Failed to call " +
                    "the Database or cast the objects to type AuctionImage ");
            ExceptionUtils.getStackTrace(e);
        }
        return auctionImages;
    }

}
