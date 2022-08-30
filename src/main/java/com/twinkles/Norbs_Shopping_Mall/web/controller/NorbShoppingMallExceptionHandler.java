package com.twinkles.Norbs_Shopping_Mall.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.ProductAlreadyExistException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.ProductDoesNotExistException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class NorbShoppingMallExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>("User not found in the repository", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NorbsShoppingMallException.class)
    public ResponseEntity<String> handleNorbsShoppingMallException(NorbsShoppingMallException norbsShoppingMallException){
        return new ResponseEntity<>(norbsShoppingMallException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<String> handleProductDoesNotExistException(ProductDoesNotExistException productDoesNotExistException){
        return new ResponseEntity<>("Product does not exist in the database.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<String> handleProductAlreadyExistException(ProductAlreadyExistException productAlreadyExistException){
        return new ResponseEntity<>("The product already exist, please check again!",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ioException){
        return new ResponseEntity<>("Please check the product image to be uploaded, unsuccessful upload.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonPatchException.class)
    public ResponseEntity<String> handleJsonPatchException(JsonPatchException jsonPatchException){
        return new ResponseEntity<>("Product could not be patched",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException jsonProcessingException){
        return new ResponseEntity<>("An error occurred during product patch processing ",HttpStatus.BAD_REQUEST);
    }

}
