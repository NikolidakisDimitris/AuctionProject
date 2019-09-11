package com.nikolidakis.requests;

import lombok.Data;

@Data
public class MarkMsgAsReadRequest extends Request {

    private String token;
    private Long msgId;


}
