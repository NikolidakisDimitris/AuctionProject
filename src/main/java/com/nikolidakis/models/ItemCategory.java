package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "Categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    @JsonIgnore(value = true)
    Long id;

    @Column(name = "category")
    @NotBlank
    String category;

    @Column(name = "auction_item_id")
    @NotBlank
    @JsonIgnore(value = true)
    Long itemId;

}
