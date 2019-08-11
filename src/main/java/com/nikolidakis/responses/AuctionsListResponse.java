package com.nikolidakis.responses;

import com.nikolidakis.models.Auction;
import lombok.Data;

import java.util.List;

@Data
public class AuctionsListResponse extends Response {

    private List<Auction> auctions;

    public AuctionsListResponse(String statusCode, String statusMsg, List<Auction> auctions) {
        super(statusCode, statusMsg);
        this.auctions = auctions;
    }
}
