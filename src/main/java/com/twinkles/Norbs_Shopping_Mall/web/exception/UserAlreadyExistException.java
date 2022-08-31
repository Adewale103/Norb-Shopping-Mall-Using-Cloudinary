package com.twinkles.Norbs_Shopping_Mall.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistException extends RuntimeException{
    private int statusCode;

    public UserAlreadyExistException(String message){
        super(message);
    }

    public UserAlreadyExistException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
