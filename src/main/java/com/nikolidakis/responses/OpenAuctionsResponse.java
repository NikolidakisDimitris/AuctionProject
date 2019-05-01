package com.nikolidakis.responses;

import com.nikolidakis.models.Auction;
import lombok.Data;

import java.util.List;

@Data
public class OpenAuctionsResponse extends Response {

    private List<Auction> auctions;

    public OpenAuctionsResponse(String statusCode, String statusMsg, List<Auction> auctions) {
        super(statusCode, statusMsg);
        this.auctions = auctions;
    }
}
