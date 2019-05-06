package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"categoryName"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    @JsonIgnore(value = true)
    Long categoryId;

    @Column(name = "categoryName")
    @NotBlank
    String categoryName;

    @JsonIgnore(value = true)
    @ManyToMany(mappedBy = "categories")
    Set<Auction> auctions = new HashSet<>();

}
