package com.nikolidakis.services;

import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.models.ItemCategory;
import com.nikolidakis.repository.ItemCategoryRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@Data
@Slf4j
public class ItemCategoryServicesImpl implements ItemCategoryServices {

    @Autowired
    private ItemCategoryRepository repository;

    @Override
    public void newCategory(String categoryName) throws ItemCategoryException {

        //check for null input
        if (isNull(categoryName)) {
            throw new ItemCategoryException("Null Category");
        }

        //find if this category already exists in the database
        boolean categoryExists = repository.existsByCategoryName(categoryName.toLowerCase());

        //if this category exists in the database then return Exception , else save it in the database
        if (categoryExists) {
            throw new ItemCategoryException("This category already exists ");
        }

        ItemCategory category = repository.save(new ItemCategory(null, categoryName.toLowerCase()));
    }

    public List<ItemCategory> findAllCategories() {
        return (List<ItemCategory>) repository.findAll();
    }

    @Override
    public ItemCategory findCategoryById(Long id) throws ItemCategoryException {

        //check for null input
        if (isNull(id)) {
            throw new ItemCategoryException("Null Category");
        }
        return repository.findByCategoryId(id).orElse(null);
    }

    @Override
    public ItemCategory findCategoryByName(String name) throws ItemCategoryException {
        //check for null input
        if (isNull(name)) {
            throw new ItemCategoryException("Null Category");
        }
        return repository.findByCategoryName(name);
    }

}
