package com.nikolidakis.repository.auctionrepository;

import com.nikolidakis.models.Auction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.nikolidakis.models.constants.DataBaseConstants.DATABASE;
import static com.nikolidakis.models.constants.LogConstants.AUCTION_REPOSITORY_CUSTOM_IMPL;
import static com.nikolidakis.models.constants.LogConstants.GET_OPEN_AUCTIONS;

@Repository
@Transactional
@Slf4j
public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Auction> getOpenAuctions() {
        List<Auction> openAuctions = null;
        log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + "prepared to call the Database ");
        try {
            TypedQuery<Auction> query =
                    (TypedQuery<Auction>) entityManager.createNativeQuery("SELECT * FROM " + DATABASE + ".Auctions where ending_time >= curdate();", Auction.class);
            openAuctions = query.getResultList();
            log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + " Database call and cast of objects to type Auction was successful ");
        } catch (Exception e) {
            log.error(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + "Failed to call the Database or cast the objects to type Auction ");
            ExceptionUtils.getStackTrace(e);
        }
        return openAuctions;
    }

    @SuppressWarnings("unchecked")
    public List<Auction> getOpenAuctionsUser() {
        List<Auction> openAuctions = null;
        log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + "prepared to call the Database ");

        TypedQuery query = (TypedQuery) entityManager.createNativeQuery("\n" +
                "SELECT Auctions.*, Users.username \n" +
                "FROM " + DATABASE + ".Auctions\n" +
                "inner join " + DATABASE + ".Users on Users.user_id = Auctions.seller_id;", Auction.class);

        return query.getResultList();
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    public Auction getAuctionById(String id){
//        Auction auction= null;
//        log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_AUCTION_BY_ID + "prepared to call the Database ");
//
//        try {
//            TypedQuery<Auction> query = (TypedQuery<Auction>) entityManager.createNativeQuery("SELECT * FROM "+DATABASE+
//                    ".auctions where auction_id="+id+";", Auction.class);
//            auction = query.getSingleResult();
//            log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_AUCTION_BY_ID + " Database call and cast of objects to type Auction was successful ");
//            System.out.println(auction);
//        } catch (Exception e) {
//            log.error(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_AUCTION_BY_ID + "Failed to call the Database or cast the objects to type Auction ");
//            ExceptionUtils.getStackTrace(e);
//        }
//    return auction;
//
//    }

}
