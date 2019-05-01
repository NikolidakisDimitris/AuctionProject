package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Auctions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "auction_id")
    Long id;

    @Column(name = "name_of_item")
    @NotBlank
    @Size(max = 30)
    String nameOfItem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    User seller; //matchItById Get token, find user, get Id

    @Column(name = "started_time")
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    String startedTime;

    @Column(name = "ending_time")
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    String endingTime;

    @Column(name = "item_description")
    @NotBlank
    @Size(max = 50)
    String itemDescription;

    @Column(name = "item_location")
    @NotBlank
    @Size(max = 30)
    String itemLocation;

    @Column(name = "item_country")
    @NotBlank
    @Size(max = 30)
    String itemCountry;


//    List<Bid> bids; //fetch it from another table


}
