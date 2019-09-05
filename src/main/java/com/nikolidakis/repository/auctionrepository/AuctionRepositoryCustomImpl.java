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
                    (TypedQuery<Auction>) entityManager.createNativeQuery("SELECT * FROM " + DATABASE + ".auctions " +
                            "where ending_time >= curdate();", Auction.class);
            openAuctions = query.getResultList();
            log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + " Database call and cast of objects to type Auction was successful ");
        } catch (Exception e) {
            log.error(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_OPEN_AUCTIONS + "Failed to call the Database or cast the objects to type Auction ");
            ExceptionUtils.getStackTrace(e);
        }
        return openAuctions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Auction> getClosedAuctions() {
        List<Auction> closedAuctions = null;
        log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_CLOSED_AUCTIONS + "prepared to call the Database ");
        try {
            TypedQuery<Auction> query =
                    (TypedQuery<Auction>) entityManager.createNativeQuery("SELECT * FROM " + DATABASE + ".auctions " +
                            "where ending_time <= curdate();", Auction.class);
            closedAuctions = query.getResultList();
            log.info(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_CLOSED_AUCTIONS + " Database call and cast of objects to " +
                    "type Auction was successful ");
        } catch (Exception e) {
            log.error(AUCTION_REPOSITORY_CUSTOM_IMPL + GET_CLOSED_AUCTIONS + "Failed to call the Database or cast the" +
                    " objects to type Auction ");
            ExceptionUtils.getStackTrace(e);
        }
        return closedAuctions;


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

        return null;
    }

    @Override
    public void deleteAuction(Auction auction) {

        TypedQuery query = (TypedQuery) entityManager.createNativeQuery("delete from " + DATABASE +
                ".auctions_categories where auction_id =?;\n");
        query.setParameter(1, auction.getId());
        int deleteCount = query.executeUpdate();

        query = (TypedQuery) entityManager.createNativeQuery("delete FROM " + DATABASE + ".auctions where auction_id = ?;");
        query.setParameter(1, auction.getId());
        int deleteCount2 = query.executeUpdate();
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


}
