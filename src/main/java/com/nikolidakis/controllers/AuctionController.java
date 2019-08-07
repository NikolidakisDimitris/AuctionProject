package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.requests.NewAuctionRequest;
import com.nikolidakis.responses.AllAuctionsResponse;
import com.nikolidakis.responses.OpenAuctionsResponse;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.AuctionServices;
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
 * New auction, delete an auction,
 */
@RestController
@RequestMapping("/auctions")
@Data
@Slf4j
public class AuctionController {

    @Autowired
    private AuctionServices services;

    //Get all the auctions , even those that are not open now
    @RequestMapping(value = "/allauctions")

    public Response getAllAuctions() throws AuctionException {
        log.info(AUCTION_CONTROLLER + GET_ALL_AUCTIONS + " going to search for all the Auctions");
        List<Auction> auctions = services.getAllAuctions();
        log.info(AUCTION_CONTROLLER + GET_ALL_AUCTIONS + "Found all the Auctions");
        return new AllAuctionsResponse(SUCCESS, "All auctions found", auctions);
    }


    //Get all the OpenAuctions -> now is before the endingTime
    @RequestMapping(value = "/openauctions")
    public Response getOpenAuctions() throws AuctionException {
        log.info(AUCTION_CONTROLLER + GET_OPEN_AUCTIONS);
        List<Auction> openAuctions = services.getOpenAuctions();
        log.info(AUCTION_CONTROLLER + GET_OPEN_AUCTIONS + "found all the open auctions");
        return new OpenAuctionsResponse(SUCCESS, "Open Auctions found", openAuctions);
    }


    @PostMapping(value = "/newauction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response newAuction(@Valid @RequestBody NewAuctionRequest request) throws AuctionException, AuthenticateException {
        log.info(AUCTION_CONTROLLER + NEW_AUCTION + "ready to create a new auction");
        log.info(request.toString());
        services.newAuction(request);
        log.info(AUCTION_CONTROLLER + NEW_AUCTION + "Auction registered successfully");
        return new Response(SUCCESS, "Auction Registered successfully");
    }
}