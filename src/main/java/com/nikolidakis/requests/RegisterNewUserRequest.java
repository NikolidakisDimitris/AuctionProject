package com.nikolidakis.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterNewUserRequest {

    @NotNull
    @Size(max = 30)
    private String username;

    @NotNull
    @Size(max = 30)
    private String password;

    @NotNull
    @Size(max = 30)
    private String firstName;

    @NotNull
    @Size(max = 30)
    private String lastName;

    @NotNull
    @Size(max = 30)
    private String email;

    @NotNull
    @Size(max = 15)
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    private String phone;

    @NotNull
    @Size(max = 30)
    private String country;

    @NotNull
    @Size(max = 30)
    private String city;

    @NotNull
    @Size(max = 50)
    private String address;

    @NotNull
    @Size(max = 30)
    private String afm;

}
