package com.nikolidakis.models;

import lombok.Data;

@Data

public class Bid {
    Long id;
    Long itemId;
    Long bidderId;
    String bidTime; //should be done localTime
    double bidPrice;

}
