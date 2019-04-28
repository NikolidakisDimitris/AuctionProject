package com.nikolidakis.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthenticateUserRequest extends Request {
    @NotNull
    @Size(max = 30)
    private String username;

    @NotNull
    @Size(max = 30)
    private String password;
}
