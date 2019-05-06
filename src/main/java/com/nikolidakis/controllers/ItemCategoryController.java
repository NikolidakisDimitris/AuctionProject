package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.requests.AddCategoryRequest;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.ItemCategoryServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.nikolidakis.models.constants.LogConstants.ADD_CATEGORY;
import static com.nikolidakis.models.constants.LogConstants.CATEGORIES_CONTROLLER;
import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;

@RestController
@RequestMapping("/categories")
@Data
@Slf4j
public class ItemCategoryController {

    @Autowired
    private ItemCategoryServices services;

    @PostMapping(value = "/addcategory",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addCategory(@Valid @RequestBody AddCategoryRequest request) throws ItemCategoryException {
        log.info(CATEGORIES_CONTROLLER + ADD_CATEGORY + " ready to add the category to the database ");
        services.newCategory(request.getCategoryName());
        log.info(CATEGORIES_CONTROLLER + ADD_CATEGORY + " Category added successesfuly to the database ");
        return new Response(SUCCESS, "Category Added successfully");
    }


}
