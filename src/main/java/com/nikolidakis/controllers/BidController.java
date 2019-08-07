package com.nikolidakis.controllers;

import com.nikolidakis.models.Bid;
import com.nikolidakis.services.BidServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/allbids")
    public List<Bid> allBids() {
        return services.getBids();
    }

}