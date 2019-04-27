package com.nikolidakis.models;

import lombok.Data;

import java.util.List;

@Data
public class Auction {
    Long id;
    String nameOfItem;
    User seller; //matchItById
    String startedTime;
    String endingTime;
    String itemDescription;
    String itemLocation;
    String itemCountry;
    List<Bid> bids; //fetch it from another table


}
