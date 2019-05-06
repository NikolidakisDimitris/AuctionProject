package com.nikolidakis.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class AddCategoryRequest extends Request {

    @NotNull
    @Size(max = 30)
    private String categoryName;

    public AddCategoryRequest(@NotNull @Size(max = 30) String categoryName) {
        this.categoryName = categoryName;
    }
}
