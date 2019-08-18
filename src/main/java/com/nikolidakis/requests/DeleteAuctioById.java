package com.nikolidakis.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAuctioById {

    @NotNull
    private Long auctionId;

    @NotNull
    private String token;
}
