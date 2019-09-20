package com.nikolidakis.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetMsgByAuctionIdRequest {
    @NotBlank
    private String token;
    @NotBlank
    private long auctionId;
}
