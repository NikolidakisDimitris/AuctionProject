package com.nikolidakis.responses;

import lombok.Data;

import java.util.List;

@Data
public class GetImagesResponse extends Response {

    private List<byte[]> data;

    public GetImagesResponse(String statusCode, String statusMsg, List<byte[]> data) {
        super(statusCode, statusMsg);
        this.data = data;
    }
}
