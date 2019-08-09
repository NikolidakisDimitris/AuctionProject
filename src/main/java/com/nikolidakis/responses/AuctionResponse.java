package com.nikolidakis.responses;

import com.nikolidakis.models.Auction;
import lombok.Data;

@Data
public class AuctionResponse extends Response {

    private Auction auction;

    public AuctionResponse(String statusCode, String statusMsg, Auction auction) {
        super(statusCode, statusMsg);
        this.auction = auction;
    }
}
