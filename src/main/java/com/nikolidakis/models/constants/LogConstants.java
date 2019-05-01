package com.nikolidakis.models.constants;

public class LogConstants {

    private LogConstants() {
    }

    public static final String USER_SERVICE = "UserServices > ";

    //Constants for the User System
    public static final String REGISTER_NEW_USER = "Method registerNewUser > ";
    public static final String USER_CONTROLER = "UserController > ";
    public static final String AUTHENTICATE_USER = "Method getToken > ";

    //Constants for the Auction
    public static final String AUCTION_CONTROLLER = "AuctionController > ";
    public static final String GET_ALL_AUCTIONS = "Method getAllAuctions > ";
    public static final String GET_OPEN_AUCTIONS = "Method getOpenAuctions > ";
    public static final String AUCTION_REPOSITORY_CUSTOM_IMPL = "AuctionRepositoryCustomImpl > ";


    //General Errors
    public static final String CLIENTERROR = "Request is not valid.";

}
