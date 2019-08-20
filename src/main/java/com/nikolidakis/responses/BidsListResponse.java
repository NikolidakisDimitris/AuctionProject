package com.nikolidakis.responses;

import com.nikolidakis.models.Bid;
import lombok.Data;

import java.util.List;

@Data
public class BidsListResponse extends Response {

    private List<Bid> bids;

    public BidsListResponse(String statusCode, String statusMsg, List<Bid> bids) {
        super(statusCode, statusMsg);
        this.bids = bids;
    }
}
