package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.models.Bid;
import com.nikolidakis.requests.GetBidsByAuctionRequest;
import com.nikolidakis.requests.NewBidRequest;
import com.nikolidakis.responses.Response;
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

import static com.nikolidakis.models.constants.LogConstants.*;
import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;

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
     *
     * Get All the bids according to the auctionId
     *
     * @param request
     * @return List<Bid>
     * @throws AuctionException
     */
    @PostMapping(value = "/getbidsbyauction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bid> getBidsByAuction(@Valid @RequestBody GetBidsByAuctionRequest request) throws AuctionException, BidException {
        log.info(BID_CONROLLER + GET_BIDS_BY_AUCTION_ID + " ready to find the auctions by the bid");
        services.getBidsByAuction(request.getAuctionId());

        log.info(BID_CONROLLER + GET_BIDS_BY_AUCTION_ID + " found the bids successfully");
        return services.getBidsByAuction(request.getAuctionId());
    }

    /**
     * Create a new bid to a specific auction
     *
     * @param request
     * @return
     * @throws AuthenticateException
     * @throws AuctionException
     */
    @PostMapping(value = "/newbid",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response newBid(@Valid @RequestBody NewBidRequest request) throws AuthenticateException, AuctionException {
        log.info(BID_CONROLLER + NEW_BID + " ready to place a new bid");
        services.newBid(request);
        log.info(BID_CONROLLER + NEW_BID + " saved successfully");
        return new Response(SUCCESS, "The bid registered successfully");
    }

}
