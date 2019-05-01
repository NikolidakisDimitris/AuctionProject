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
    Long id;

    @Column(name = "auction_item_id")
    @NotBlank
    Long itemId;

//    @Column(name = "bidder_id")
//    @NotBlank
//    Long bidderId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bidder_id", referencedColumnName = "user_id")
    User bidder;

    @Column(name = "bid_time")
    @NotBlank
    String bidTime; //should be done localTime

    @Column(name = "bid_price")
    @NotBlank
    double bidPrice;

}
