package com.twinkles.Norbs_Shopping_Mall.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductDoesNotExistException extends NorbsShoppingMallException{
    private int statusCode;
    public ProductDoesNotExistException(String message) {
        super(message);
    }
    public ProductDoesNotExistException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
