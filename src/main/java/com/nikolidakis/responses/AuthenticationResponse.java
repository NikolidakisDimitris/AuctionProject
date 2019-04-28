package com.nikolidakis.responses;

import lombok.Data;

@Data
public class AuthenticationResponse extends Response {

    //TODO: Has to change and give token insted of true or false. True or false is just for testing
    private boolean isAuthenticated;


    public AuthenticationResponse(String statusCode, String statusMsg, boolean isAuthenticated) {
        super(statusCode, statusMsg);
        this.isAuthenticated = isAuthenticated;
    }
}
