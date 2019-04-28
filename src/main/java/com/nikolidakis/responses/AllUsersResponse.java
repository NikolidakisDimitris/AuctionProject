package com.nikolidakis.responses;

import com.nikolidakis.models.User;
import lombok.Data;

import java.util.List;

@Data
public class AllUsersResponse extends Response {

    private List<User> users;

    public AllUsersResponse(String statusCode, String statusMsg, List<User> users) {
        super(statusCode, statusMsg);
        this.users = users;
    }
}
