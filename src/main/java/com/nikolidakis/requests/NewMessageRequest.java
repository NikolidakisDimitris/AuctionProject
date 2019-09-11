package com.nikolidakis.requests;

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
    private Long auctionId;
    @NotNull
    private String subject;
    @NotNull
    private String message;


}
