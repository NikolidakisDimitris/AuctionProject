package com.nikolidakis.repository.auctionrepository;

import com.nikolidakis.models.Auction;
import com.nikolidakis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.nikolidakis.models.constants.DataBaseConstants.DATABASE;
import static com.nikolidakis.models.constants.LogConstants.*;

@Repository
@Transactional
@Slf4j
public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom {

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public Auction findAuctionById(Long id) {
//        Auction auction = null;
        log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + FIND_AUCTION_BY_ID + "prepared to call the Database ");
        Query query = entityManager.createNativeQuery("SELECT auctions.*,\n" +
                "bids.*\n" +
                "FROM auctions\n" +
                "left join bids \n" +
                "on auctions.auction_id = bids.auction_item_id\n" +
                "where auctions.auction_id = ? ;");
        query.setParameter(1, id);
        Object[] auction = (Object[]) query.getSingleResult();


        System.out.println("Repository Custom Implementation . the auction is");
        for (Object element : auction) {
            System.out.println(element);
        }

//        List<Auction> auctions = query.getResultList();
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Auction> getOpenAuctionsUser() {
        List<Auction> openAuctions = null;
        log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + "prepared to call the Database ");

        TypedQuery query = (TypedQuery) entityManager.createNativeQuery("\n" +
                "SELECT auctions.*, users.username \n" +
                "FROM " + DATABASE + ".auctions\n" +
                "inner join " + DATABASE + ".users on users.user_id = auctions.seller_id;", Auction.class);

        return query.getResultList();
    }


//    private Auction parseSQLResultToAuction(Object [] result){
//        if (!isNull(result)){
//            //------- From 1 to 5  -----
//            Long id = (Long) result[0];
//            String endingTime = (String) result[1];
//            String itemCountry = (String) result[2];
//            String item_description = (String) result[3];
//            String itemLocation = (String) result[4];
//
//            //--------- from 6 to 10 -----
//            String nameOfItem = (String) result[5];
//            String startedTime = (String) result[6];
//            Long sellerId = (Long) result[7];
//            User seller = userRepository.findById(sellerId).orElse(null);
//            String id = (String) result[8];
//            String id = (String) result[9];
//
//            //--------from 11 to 13 ------
//            String String = (String) result[10];
//            String String = (String) result[11];
//            String String = (String) result[12];
//
//        }
//
//
//        return
//    }


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
