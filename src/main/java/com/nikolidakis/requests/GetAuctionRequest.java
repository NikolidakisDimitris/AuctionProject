package com.nikolidakis.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAuctionRequest extends Request {

    private String auctionId;

}