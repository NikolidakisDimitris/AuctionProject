package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.exceptions.MessageException;
import com.nikolidakis.models.Message;
import com.nikolidakis.requests.GetUserMsgsRequest;
import com.nikolidakis.requests.NewMessageRequest;
import com.nikolidakis.responses.AllMessageResponse;
import com.nikolidakis.responses.MessagesResponse;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.MessageServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;

@RestController
@RequestMapping("/message")
@Data
@Slf4j
public class MessageController {

    @Autowired
    private MessageServices messageServices;

    //Method to return all the auctions
    @RequestMapping(value = "/allmessages")
    public Response getAllMessages() throws AuctionException {
        log.info(MESSAGE_CONTROLLER + GET_ALL_MESSAGES + "going to search for all the Messages");
        List<Message> messages = messageServices.getAllMessages();
        log.info(MESSAGE_CONTROLLER + GET_ALL_MESSAGES + "Found all the Auctions");
        return new AllMessageResponse(SUCCESS, "All messages found", messages);
    }

    //Method to sent a new message
    //check if the auction is closed , and the user is the winner
    @PostMapping(value = "/newmessage",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response sendingNewMessage(@Valid @RequestBody NewMessageRequest request) throws AuctionException,
            AuthenticateException, MessageException, BidException {
        log.info(MESSAGE_CONTROLLER + SENDING_NEW_MESSAGE + "Ready to sent a new message");
        messageServices.sendingNewMessage(request.getToken(), request.getAuction(), request.getSubject(), request.getMessage());
        log.info(MESSAGE_CONTROLLER + SENDING_NEW_MESSAGE + "new message sent successfully");
        return new Response(SUCCESS, "The message sent successfully");
    }

    //Method to get the inbox
    @PostMapping(value = "/getinbox",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getInbox(GetUserMsgsRequest request) throws AuthenticateException {
        log.info(MESSAGE_CONTROLLER + GET_USER_INBOX + "Ready to get the user inbox ");
        List<Message> messages = messageServices.getInbox(request.getToken());
        log.info(MESSAGE_CONTROLLER + GET_USER_INBOX + "user inbox fetched successfully");
        return new MessagesResponse(SUCCESS, "The inbox retrieved successfully", messages);
    }
    //Method to get the sent messages

    @PostMapping(value = "/getsent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getSentMsgs(GetUserMsgsRequest request) throws AuthenticateException {
        log.info(MESSAGE_CONTROLLER + GET_USER_SENT_MSG + "Ready to get the sent messages ");
        List<Message> messages = messageServices.getInbox(request.getToken());
        log.info(MESSAGE_CONTROLLER + GET_USER_SENT_MSG + "sent messages fetched successfully");
        return new MessagesResponse(SUCCESS, "Sent messages retrieved successfully", messages);
    }


    //method


}
