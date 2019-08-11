package com.nikolidakis.controllers;

import com.nikolidakis.models.Bid;
import com.nikolidakis.requests.GetBidsByAuctionRequest;
import com.nikolidakis.services.BidServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.BID_CONROLLER;
import static com.nikolidakis.models.constants.LogConstants.GET_ALL_BIDS;

/**
 * Just give a bid
 */

@RestController
@RequestMapping("/bids")
@Data
@Slf4j
public class BidController {

    @Autowired
    private final BidServices services;


    /**
     * Get All Bids. Just for testing
     */
    @RequestMapping("/allbids")
    public List<Bid> allBids() {
        log.info(BID_CONROLLER + GET_ALL_BIDS);
        return services.getBids();
    }

    /**
     * Probably not any reason to implement it. if you get the auction , the auction also carry a list with all the
     * bids.
     * Need to remove any related classes
     */
    @PostMapping(value = "/getbidsbyauction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bid> getBidsByAuction(@Valid @RequestBody GetBidsByAuctionRequest request) {

        return services.getBids();
    }

//TODO : Needs implementation cause it is not complete.
//    @PostMapping(value = "/newbid",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public void newBid(@Valid @RequestBody NewBidRequest request) {
//        log.info(BID_CONROLLER+NEW_BID+" ready to place a new bid");
//        services.newBid(request);
//        return services.getBids();
//    }

}
