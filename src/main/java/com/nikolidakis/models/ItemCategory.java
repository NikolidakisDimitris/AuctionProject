package com.nikolidakis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "Categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id"})})
@Getter
@Setter
@NoArgsConstructor
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    @NotBlank
    private String categoryName;

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Auction> auctions = new HashSet<>();

    @Column(name = "image_path")
    private String imagePath;

    public ItemCategory(Long categoryId, @NotBlank String categoryName, String imagePath) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imagePath = imagePath;
    }

    public ItemCategory(Long categoryId, @NotBlank String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemCategory other = (ItemCategory) obj;
        return Objects.equals(categoryId, other.categoryId);
    }

}
