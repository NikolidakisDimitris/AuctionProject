package com.nikolidakis.responses;

import com.nikolidakis.models.Message;
import lombok.Data;

import java.util.List;

@Data
public class MessagesResponse extends Response {

    List<Message> messages;

    public MessagesResponse(String statusCode, String statusMsg, List<Message> messages) {
        super(statusCode, statusMsg);
        this.messages = messages;
    }
}
