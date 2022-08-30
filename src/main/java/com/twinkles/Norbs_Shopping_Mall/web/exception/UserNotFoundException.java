package com.twinkles.Norbs_Shopping_Mall.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends NorbsShoppingMallException{
    private int statusCode;
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
