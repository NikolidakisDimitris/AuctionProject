package com.nikolidakis.requests;

import com.nikolidakis.responses.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBidRequest extends Response {

    @NotNull
    private String bidderToken;
    @NotNull
    private String bidderValue;
    @NotNull
    private String auctionId;
}
