package com.nikolidakis.responses;

import com.nikolidakis.models.ItemCategory;
import lombok.Data;

@Data
public class GetCategoryResponse extends Response {

    private ItemCategory category;

    public GetCategoryResponse(String statusCode, String statusMsg, ItemCategory category) {
        super(statusCode, statusMsg);
        this.category = category;
    }
}
