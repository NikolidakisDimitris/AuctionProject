package com.nikolidakis.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ItemCategoryException;
import com.nikolidakis.exceptions.UserException;
import com.nikolidakis.responses.ErrorResponse;
import com.nikolidakis.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.nikolidakis.models.constants.LogConstants.CLIENTERROR;
import static com.nikolidakis.models.constants.StatusCodes.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    private final ObjectMapper jsonMapper;

    @Autowired
    private ExceptionHandlerController(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    //#################################### GENERAL EXCEPTIONS ##############################################################
    //BACKEND_ERROR
    @ExceptionHandler
    public ResponseEntity<Response> handleBackendErrorException(Exception exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(BACKEND_ERROR, exception.getMessage());
        log.info("Returning output: {}" + exception.getClass(), jsonMapper.writeValueAsString(omResponse));
        exception.printStackTrace();
        return new ResponseEntity<>(omResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //CLIENT_ERROR
    @ExceptionHandler
    public ResponseEntity<Response> handleClientException(MethodArgumentNotValidException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(CLIENT_ERROR, CLIENTERROR);
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.BAD_REQUEST);
    }

    //CLIENT_ERROR
    @ExceptionHandler
    public ResponseEntity<Response> handleClientException(ConstraintViolationException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(CLIENT_ERROR, CLIENTERROR);
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.BAD_REQUEST);
    }


    //CLIENT_ERROR
    @ExceptionHandler
    public ResponseEntity<Response> handleClientException(HttpMessageNotReadableException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(CLIENT_ERROR, CLIENTERROR);
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.BAD_REQUEST);
    }
//############################# END OF GENERAL EXCEPTIONS ##############################################################


    //##################################### CUSTOM EXCEPTIONS ##############################################################
    //UserException Exception
    @ExceptionHandler
    public ResponseEntity<Response> userException(UserException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(CLIENT_ERROR, exception.getMessage());
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.BAD_REQUEST);
    }

    //Authentication_Exception
    @ExceptionHandler
    public ResponseEntity<Response> notValidAuthenticationException(AuthenticateException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(AUTHENTICATION_FAILED, exception.getMessage());
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.UNAUTHORIZED);
    }

    //AuctionException
    @ExceptionHandler
    public ResponseEntity<Response> auctionException(AuctionException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(AUCTION_EXCEPTION, exception.getMessage());
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    //AuctionException
    @ExceptionHandler
    public ResponseEntity<Response> itemCategoryException(ItemCategoryException exception) throws JsonProcessingException {
        ErrorResponse omResponse = new ErrorResponse(ITEM_CATEGORY_EXCEPTION, exception.getMessage());
        log.info("Returning output: {}", jsonMapper.writeValueAsString(omResponse));
        return new ResponseEntity<>(omResponse, HttpStatus.BAD_REQUEST);
    }

}
