package com.nikolidakis.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    private List<String> categories;

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

    @NotNull
    @Size(max = 30)
    private String itemLocation;

    @NotNull
    @Size(max = 30)
    private String itemCountry;

}
