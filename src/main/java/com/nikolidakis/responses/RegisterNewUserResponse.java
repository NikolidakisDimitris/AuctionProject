package com.nikolidakis.responses;

import lombok.Data;

@Data
public class RegisterNewUserResponse extends Response {

    private String token;


    public RegisterNewUserResponse(String statusCode, String statusMsg, String token) {
        super(statusCode, statusMsg);
        this.token = token;
    }
}
