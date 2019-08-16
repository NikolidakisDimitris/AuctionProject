package com.nikolidakis.services;

import com.nikolidakis.models.Bid;
import com.nikolidakis.repository.BidRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.BID_SERVICES;
import static com.nikolidakis.models.constants.LogConstants.GET_ALL_BIDS;

@Service
@Data
@Slf4j
public class BidServicesImpl implements BidServices {

    @Autowired
    private final BidRepository repository;
    @Autowired
    private final UserServices userServices;

    public List<Bid> getBids() {
        log.info(BID_SERVICES + GET_ALL_BIDS + " ready to get all the bids");
        return (List<Bid>) repository.findAll();

    }

    //TODO: needs implementation.Has been implemented in the auction controller. Probably Will be removed.
//    @Override
//    public void newBid(NewBidRequest request) throws AuthenticateException {
//        log.info(BID_SERVICES + NEW_BID + " ready to place a new bid");
//        User bidder = userServices.findUserByToken(request.getBidderToken());
//        Bid bid = new Bid(null, bidder, LocalDate.now().toString(), request.getBidderValue());
//
////        repository.save()
//    }
}
