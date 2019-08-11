package com.nikolidakis.repository;


import com.nikolidakis.models.ItemCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemCategoryRepository extends CrudRepository<ItemCategory, Long> {

    boolean existsByCategoryName(String categoryName);

    ItemCategory findByCategoryName(String categoryName);

    Optional<ItemCategory> findByCategoryId(Long id);
}
