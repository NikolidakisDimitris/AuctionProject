package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Bids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bid_id")
    private Long bid_id;

//    @Column(name = "auction_item_id")
//    @NotBlank
//    Long bid_itemId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bidder_id", referencedColumnName = "user_id")
    private User bidder;

    @Column(name = "bid_time")
    @NotBlank
    private String bidTime; //should be done localTime

    @Column(name = "bid_price")
    @NotBlank
    private double bidPrice;

    //    @Column(name = "auction_id")
//    @NotBlank
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_item_id", referencedColumnName = "auction_ID")
    private Auction auction;

}
