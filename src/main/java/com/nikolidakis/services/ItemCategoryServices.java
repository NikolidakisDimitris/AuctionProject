package com.nikolidakis.services;

import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.models.ItemCategory;

import java.util.List;

public interface ItemCategoryServices {

    void newCategory(String category) throws ItemCategoryException;

    List<ItemCategory> findAllCategories();

    ItemCategory findCategoryById(Long id) throws ItemCategoryException;

    ItemCategory findCategoryByName(String name) throws ItemCategoryException;

}
