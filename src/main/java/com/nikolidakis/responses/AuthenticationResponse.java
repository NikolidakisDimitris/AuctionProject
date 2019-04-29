package com.nikolidakis.responses;

import lombok.Data;

@Data
public class AuthenticationResponse extends Response {

    private String token;


    public AuthenticationResponse(String statusCode, String statusMsg, String token) {
        super(statusCode, statusMsg);
        this.token = token;
    }
}
