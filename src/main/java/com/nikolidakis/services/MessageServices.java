package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.BidException;
import com.nikolidakis.exceptions.MessageException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.Message;

import java.util.List;

public interface MessageServices {

    public List<Message> getAllMessages();

    public void sendingNewMessage(String token, Auction auction, String subject, String message) throws AuthenticateException
            , MessageException,
            BidException, AuctionException;

    public List<Message> getInbox(String token) throws AuthenticateException;

    public List<Message> getSentMsgs(String token) throws AuthenticateException;

    public List<Message> getUnreadMsgs(String token) throws AuthenticateException;



}