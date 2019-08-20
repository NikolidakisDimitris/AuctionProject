package com.nikolidakis.requests;

import com.nikolidakis.models.Auction;
import com.nikolidakis.responses.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMessageRequest extends Response {
    @NotNull
    private String token;
    @NotNull
    private Auction auction;
    @NotNull
    private String subject;
    @NotNull
    private String message;


}
