package com.nikolidakis.repository;


import com.nikolidakis.models.ItemCategory;
import org.springframework.data.repository.CrudRepository;

public interface ItemCategoryRepository extends CrudRepository<ItemCategory, Long> {

    boolean existsByCategoryName(String categoryName);
}
