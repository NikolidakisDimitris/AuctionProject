package com.nikolidakis.responses;

import com.nikolidakis.models.ItemCategory;
import lombok.Data;

import java.util.List;

@Data
public class CategoriesResponse extends Response {

    private List<ItemCategory> categories;

    public CategoriesResponse(String statusCode, String statusMsg, List<ItemCategory> categories) {
        super(statusCode, statusMsg);
        this.categories = categories;
    }
}
