package com.nikolidakis.responses;

import lombok.Data;

@Data
public class ErrorResponse extends Response {

    public ErrorResponse(String statusCode, String statusMsg) {
        super(statusCode, statusMsg);
    }

}
