package com.nikolidakis.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAuctionsByFieldRequest {
    @NotNull
    private String fieldName;
    @NotNull
    private String fieldValue;
}
