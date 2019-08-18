package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.requests.DeleteAuctioById;
import com.nikolidakis.requests.GetAuctionRequest;
import com.nikolidakis.requests.GetAuctionsByFieldRequest;
import com.nikolidakis.requests.NewAuctionRequest;
import com.nikolidakis.responses.AuctionResponse;
import com.nikolidakis.responses.AuctionsListResponse;
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

    /**
     * Method to get ALL the auctions that exist  in the database, even those that are not open now.
     *
     * @return AuctionsListResponse
     * @throws AuctionException
     */

    @RequestMapping(value = "/allauctions")
    public Response getAllAuctions() throws AuctionException {
        log.info(AUCTION_CONTROLLER + GET_ALL_AUCTIONS + " going to search for all the Auctions");
        List<Auction> auctions = services.getAllAuctions();
        log.info(AUCTION_CONTROLLER + GET_ALL_AUCTIONS + "Found all the Auctions");
        return new AuctionsListResponse(SUCCESS, "All auctions found", auctions);
    }


    /**
     * Method to get ALL the OPEN actions (ending date-time isAfter the current date-time)
     *
     * @return AuctionsListResponse
     * @throws AuctionException
     */
    @RequestMapping(value = "/openauctions")
    public Response getOpenAuctions() throws AuctionException {
        log.info(AUCTION_CONTROLLER + GET_OPEN_AUCTIONS);
        List<Auction> openAuctions = services.getOpenAuctions();
        log.info(AUCTION_CONTROLLER + GET_OPEN_AUCTIONS + "found all the open auctions");
        return new AuctionsListResponse(SUCCESS, "Open Auctions found", openAuctions);
    }


    /**
     * Method to create a new Auction in the database
     *
     * @param #request
     * @return Response
     * @throws AuctionException
     * @throws AuthenticateException
     */
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


    /**
     *
     * Method to get A specific Auction by its id,
     *
     * @param request
     * @return
     * @throws AuctionException
     */
    @PostMapping(value = "/getauctionbyid",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAuctionById(@Valid @RequestBody GetAuctionRequest request) throws AuctionException {
        System.out.println(request);
        log.info(AUCTION_CONTROLLER + GET_AUCTION_BY_ID + "ready to create a new auction");
        Auction auction = services.getAuctionById(request.getAuctionId());
        log.info(AUCTION_CONTROLLER + GET_AUCTION_BY_ID + "suction returned Successfully");
        return new AuctionResponse(SUCCESS, "Auction returned Successfully", auction);
    }


    /**
     *
     * Method to get ALL the Auctions by category, by sellerId, by bidder id,
     *
     * @param request
     * @return
     * @throws AuthenticateException
     */
    @PostMapping(value = "/getauctionsbyfield",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAuctionsByField(@Valid @RequestBody GetAuctionsByFieldRequest request) throws AuthenticateException {
        log.info(AUCTION_CONTROLLER + GET_AUCTIONS_BY_FIELD + "ready get the auctions by " + request.getFieldName());
        List<Auction> auctions = services.getAuctionsByField(request);
        log.info(AUCTION_CONTROLLER + GET_AUCTIONS_BY_FIELD + "auctions returned Successfully");
        return new AuctionsListResponse(SUCCESS, "Auction returned Successfully", auctions);
    }

    @PostMapping(value = "/deleteauctionbyid",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteAuctionById(@Valid @RequestBody DeleteAuctioById request) throws AuthenticateException, AuctionException {
        log.info(AUCTION_CONTROLLER + DELETE_AUCTION_BY_ID + "ready to delete the auction with id" + request);
        services.deleteAuctionById(request.getAuctionId(), request.getToken());
        log.info(AUCTION_CONTROLLER + DELETE_AUCTION_BY_ID + "auctions deleted Successfully");
        return new Response(SUCCESS, "Auction deleted Successfully");
    }
}
