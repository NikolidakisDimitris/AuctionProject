package com.nikolidakis.responses;

import com.nikolidakis.models.User;
import lombok.Data;

@Data
public class AuthenticationResponse extends Response {

    private String token;
    private User user;


    public AuthenticationResponse(String statusCode, String statusMsg, String token, User user) {
        super(statusCode, statusMsg);
        this.token = token;
        this.user = user;
    }
}
