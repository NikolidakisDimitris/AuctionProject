package com.nikolidakis.repository;


import com.nikolidakis.models.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

    boolean existsByCategoryName(String categoryName);
}
