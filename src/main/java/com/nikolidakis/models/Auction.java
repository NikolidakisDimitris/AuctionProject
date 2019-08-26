package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Auctions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "initial_price")
    double initialPrice;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "auction")
    @JsonManagedReference
    Set<Bid> bids;


    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "auctions_categories",
            joinColumns = {@JoinColumn(name = "auction_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    Set<ItemCategory> categories = new HashSet<>();

    public Auction(Long id, @NotBlank @Size(max = 30) String nameOfItem, User seller,
                   @NotBlank String startedTime, @NotBlank String endingTime,
                   @NotBlank @Size(max = 50) String itemDescription, @NotBlank @Size(max = 30) String itemLocation,
                   @NotBlank @Size(max = 30) String itemCountry,
                   double initialPrice, Set<ItemCategory> categories) {
        this.id = id;
        this.nameOfItem = nameOfItem;
        this.seller = seller;
        this.startedTime = startedTime;
        this.endingTime = endingTime;
        this.itemDescription = itemDescription;
        this.itemLocation = itemLocation;
        this.itemCountry = itemCountry;
        this.initialPrice = initialPrice;
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Auction other = (Auction) obj;
        return Objects.equals(id, other.id);
    }

}
