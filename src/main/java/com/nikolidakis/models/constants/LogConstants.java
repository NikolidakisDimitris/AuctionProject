package com.nikolidakis.models.constants;

public class LogConstants {

    private LogConstants() {
    }


    //Constants for the User System
    public static final String USER_CONTROLER = "UserController > ";
    public static final String USER_SERVICE = "UserServices > ";
    public static final String REGISTER_NEW_USER = "Method registerNewUser > ";
    public static final String AUTHENTICATE_USER = "Method getToken > ";
    public static final String FIND_USER_BY_TOKEN = "Method findUserByToken > ";


    //Constants for the Auction
    public static final String AUCTION_CONTROLLER = "AuctionController > ";
    public static final String GET_ALL_AUCTIONS = "Method getAllAuctions > ";
    public static final String GET_OPEN_AUCTIONS = "Method getOpenAuctions > ";
    public static final String AUCTION_REPOSITORY_CUSTOM_IMPL = "AuctionRepositoryCustomImpl > ";
    public static final String NEW_AUCTION = "Method newAuction > ";
    public static final String AUCTION_SERVICES = "AuctionServices > ";

    //Constants for the Categories
    public static final String CATEGORIES_SERVICES = "ItemCategoriesServices > ";
    public static final String CATEGORIES_CONTROLLER = "ItemCategoriesController > ";
    public static final String ADD_CATEGORY = " Method Add category > ";
    public static final String GET_CATEGORY = " Method Get category > ";


    //General Errors
    public static final String CLIENTERROR = "Request is not valid.";

}