package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Bids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long bidId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bidder_id", referencedColumnName = "user_id")
    private User bidder;

    @Column(name = "bid_time")
    @NotNull
    private String bidTime; //should be done localTime

    @Column(name = "bid_price")
    @NotNull
    private double bidPrice;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_item_id", referencedColumnName = "auction_id")
    @JsonBackReference
    private Auction auction;

    @Override
    public int hashCode() {
        return Objects.hashCode(bidId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bid other = (Bid) obj;
        return Objects.equals(bidId, other.bidId);
    }

}
