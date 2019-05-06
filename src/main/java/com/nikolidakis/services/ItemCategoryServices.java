package com.nikolidakis.services;

import com.nikolidakis.exceptions.ItemCategoryException;

public interface ItemCategoryServices {

    void newCategory(String category) throws ItemCategoryException;

}
