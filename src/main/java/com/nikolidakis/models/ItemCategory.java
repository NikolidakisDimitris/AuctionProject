package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "categoryName")
    @NotBlank
    private String categoryName;

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Auction> auctions = new HashSet<>();

    public ItemCategory(Long categoryId, @NotBlank String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
