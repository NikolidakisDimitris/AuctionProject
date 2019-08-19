package com.nikolidakis.requests;

import com.nikolidakis.models.Auction;
import com.nikolidakis.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateUserRequest extends Request {

    @NotNull
    private String userToken;
    @NotNull
    private User userToBeRated;
    @NotNull
    private Auction auction;
    @NotNull
    @Min(0)
    @Max(5)
    private int rate;
}
