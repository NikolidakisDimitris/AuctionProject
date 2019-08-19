package com.nikolidakis.responses;

import com.nikolidakis.models.Bid;
import lombok.Data;

@Data
public class BidResponse extends Response {

    private Bid bid;

    public BidResponse(String statusCode, String statusMsg, Bid bid) {
        super(statusCode, statusMsg);
        this.bid = bid;
    }
}
