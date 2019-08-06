package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Table(name = "Categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"categoryName"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "categoryName")
    @NotBlank
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<Auction> auctions;

    public ItemCategory(Long categoryId, @NotBlank String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
