package com.nikolidakis.responses;

import com.nikolidakis.models.Message;
import lombok.Data;

import java.util.List;

@Data
public class AllMessageResponse extends Response {

    private List<Message> messages;

    public AllMessageResponse(String statusCode, String statusMsg, List<Message> messages) {
        super(statusCode, statusMsg);
        this.messages = messages;
    }
}
