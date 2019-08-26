package com.nikolidakis.responses;

import com.nikolidakis.models.Auction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionsListResponse extends Response {

    private List<Auction> auctions = new ArrayList<>();


    public AuctionsListResponse(String statusCode, String statusMsg, List<Auction> auctions) {
        super(statusCode, statusMsg);
        this.auctions = auctions;
    }
}
