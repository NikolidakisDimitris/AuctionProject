package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "auction_item_id")
//    List<ItemCategory> categories;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_item_id")
    List<Bid> bids; //fetch it from another table

    //TODO: WARNING  !!!!! need to change it somehow to updata both tables. Neeed to check the new category method, and
    // the
    // new
    // Auction method
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Auctions_Categories",
            joinColumns = {@JoinColumn(name = "auction_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    Set<ItemCategory> categories = new HashSet<>();


}
