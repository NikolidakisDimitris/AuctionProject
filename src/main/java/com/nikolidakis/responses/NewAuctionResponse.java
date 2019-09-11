package com.nikolidakis.responses;

import lombok.Data;

@Data
public class NewAuctionResponse extends Response {


    private Long auctionId;


    public NewAuctionResponse(String statusCode, String statusMsg, Long auctionId) {
        super(statusCode, statusMsg);
        this.auctionId = auctionId;
    }
}
