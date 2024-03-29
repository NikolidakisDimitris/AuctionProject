package com.nikolidakis.requests;

import com.nikolidakis.models.ItemCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
public class NewAuctionRequest {

    @NotNull
    @Size(max = 512)
    private String token;

    @NotNull
    @Size(max = 30)
    private String nameOfItem;

    @NotNull
    private Set<ItemCategory> categories;

    @NotNull
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private String startedTime;

    @NotNull
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private String endingTime;

    @NotNull
    @Size(max = 50)
    private String itemDescription;

    @Min(0)
    private double initialPrice;

}
