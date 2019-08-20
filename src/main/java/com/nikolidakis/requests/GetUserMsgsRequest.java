package com.nikolidakis.requests;

import lombok.Data;

@Data
public class GetUserMsgsRequest extends Request {

    private String token;
}
