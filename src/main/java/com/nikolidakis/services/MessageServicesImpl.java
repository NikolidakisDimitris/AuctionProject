package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.exceptions.MessageException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Message;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.MessageRepository;
import com.nikolidakis.utils.Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;

@Service
@Data
@Slf4j
public class MessageServicesImpl implements MessageServices {

    private MessageRepository messageRepository;
    private UserServices userServices;
    //    private AuctionServices auctionServices;
    private BidServices bidServices;

    @Autowired
    public MessageServicesImpl(MessageRepository messageRepository, UserServices userServices, BidServices bidServices) {
        this.messageRepository = messageRepository;
        this.userServices = userServices;
//        this.auctionServices = auctionServices;
        this.bidServices = bidServices;
    }


    @Override
    public List<Message> getAllMessages() {
        log.info(MESSAGE_SERVICES + GET_ALL_MESSAGES + "ready to get the messages from the database");
        List<Message> messages = (List<Message>) messageRepository.findAll();
        log.info(MESSAGE_SERVICES + GET_ALL_MESSAGES + "messages has been retrieved from the database");
        return messages;
    }

    @Override
    public void sendingNewMessage(String token, Auction auction, String subject, String message) throws AuthenticateException,
            MessageException, BidException, AuctionException {

        //find the user who want to sent a message
        User currentUser = userServices.findUserByToken(token);
        User receiver = null;

        //check if the auction has ended
        LocalDateTime endingTime = Utils.stringDateToLocalDate(auction.getEndingTime());

        LocalDateTime now = LocalDateTime.now();

        if (endingTime.isAfter(now)) {
            log.info(MESSAGE_SERVICES + SENDING_NEW_MESSAGE + "The auction is still running, so no message can be sent");
            throw new MessageException("The auction is still running, so no message can be sent");
        }

        //find the winner of the auction
        User winner = bidServices.getHighestBid(auction.getId()).getBidder();

        //1. check if the user who is going to sent the msg is the winner, and the receiver is the seller
        //The winner want to sent to the seller
        if (currentUser.getId().equals(winner.getId())) {
            log.info(MESSAGE_SERVICES + SENDING_NEW_MESSAGE + "The bidder want to sent to the seller. Sender " +
                    "= bidder . Receiver = seller");
            //Receiver =  seller
            receiver = userServices.findUserById(auction.getSeller().getId());
        }

        //2. check if the user who is going to sent the msg is the seller, and the receiver is the winner
        else if (currentUser.getId().equals(auction.getSeller().getId())) {
            log.info(MESSAGE_SERVICES + SENDING_NEW_MESSAGE + "The seller want to sent to the winner of the auction. " +
                    "Sender = seller , Receiver = Bidder");
            //Receiver is the winner (highest bidder)
            receiver = winner;

        } else {
            log.info(MESSAGE_SERVICES + SENDING_NEW_MESSAGE + "A general error occured  ");
            throw new MessageException("You can not sent a message . Neither a seller, nor a winner of the auction");
        }

        //Sent the message
        String msgSubject = "Auction id " + auction.getId() + " : " + subject;
        Message messageObj = new Message(null, LocalDateTime.now().toString(), currentUser, receiver, msgSubject,
                message);
        log.info(MESSAGE_SERVICES + SENDING_NEW_MESSAGE + "Ready to save the new message");
        Message savedMsg = messageRepository.save(messageObj);
        log.info(MESSAGE_SERVICES + SENDING_NEW_MESSAGE + "Message from {} to {} saved successfully",
                savedMsg.getSender().getUsername(), savedMsg.getReceiver().getUsername());

    }

    @Override
    public List<Message> getInbox(String token) throws AuthenticateException {
        log.info(MESSAGE_SERVICES + GET_USER_INBOX + "Ready to get the inbox messages");
        //find user by token
        User receiver = userServices.findUserByToken(token);

        //find the messages for this receiver
        List<Message> messages = messageRepository.findByReceiver(receiver);
        log.info(MESSAGE_SERVICES + GET_USER_INBOX + "Fetched successfully the inbox messages");

        return messages;
    }

    @Override
    public List<Message> getSentMsgs(String token) throws AuthenticateException {
        log.info(MESSAGE_SERVICES + GET_USER_SENT_MSG + "Ready to get the sent messages");
        //find user by token
        User sender = userServices.findUserByToken(token);

        //find the messages for this sender
        List<Message> messages = messageRepository.findBySender(sender);
        log.info(MESSAGE_SERVICES + GET_USER_SENT_MSG + "Fetched successfully the sent messages");

        return messages;
    }
}
