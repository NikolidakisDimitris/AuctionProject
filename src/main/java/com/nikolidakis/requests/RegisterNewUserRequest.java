package com.nikolidakis.requests;

import com.nikolidakis.models.User;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterNewUserRequest {

    @NotNull
    private User user;
}
