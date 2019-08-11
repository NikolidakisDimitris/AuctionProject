package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.models.ItemCategory;
import com.nikolidakis.requests.AddCategoryRequest;
import com.nikolidakis.requests.GetUserByIdRequest;
import com.nikolidakis.responses.CategoriesResponse;
import com.nikolidakis.responses.GetCategoryResponse;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.ItemCategoryServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;

@RestController
@RequestMapping("/itemcategories")
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
        log.info(CATEGORIES_CONTROLLER + ADD_CATEGORY + " Category added successfully to the database ");
        return new Response(SUCCESS, "Category Added successfully");
    }

    @RequestMapping(value = "/allcategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCategories() {
        log.info(CATEGORIES_CONTROLLER + GET_CATEGORIES + "ready to get all the categories");
        List<ItemCategory> categoryList = services.findAllCategories();
        log.info(CATEGORIES_CONTROLLER + GET_CATEGORIES + "found the categories, and ready to return them");
        return new CategoriesResponse(SUCCESS, "Successful return of the categories", categoryList);
    }

    //TODO: needs bug fix
    @PostMapping(value = "/getcategorybyid",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCategoryById(@Valid @RequestBody GetUserByIdRequest request) throws ItemCategoryException {
        log.info(CATEGORIES_CONTROLLER + GET_CATEGORY_BY_ID + " ready to get the category from the database from ID ");
        ItemCategory category = services.findCategoryById(request.getId());
        log.info(CATEGORIES_CONTROLLER + GET_CATEGORY_BY_ID + " Category retrieved successfully from the database " +
                "from ID");
        return new GetCategoryResponse(SUCCESS, "Category Retrieved successfully", category);
    }


    @PostMapping(value = "/getcategorybyname",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCategoryByName(@Valid @RequestBody AddCategoryRequest request) throws ItemCategoryException {
        log.info(CATEGORIES_CONTROLLER + GET_CATEGORY_BY_NAME + " ready to get the category from the database from name");
        ItemCategory category = services.findCategoryByName(request.getCategoryName());
        log.info(CATEGORIES_CONTROLLER + GET_CATEGORY_BY_NAME + " Category retrieved successfully to the database ");
        return new GetCategoryResponse(SUCCESS, "Category Retrieved successfully", category);
    }

}
