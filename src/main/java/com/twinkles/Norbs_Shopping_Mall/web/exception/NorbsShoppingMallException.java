package com.twinkles.Norbs_Shopping_Mall.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NorbsShoppingMallException extends RuntimeException{
    private int statusCode;
    public NorbsShoppingMallException(String message){
        super(message);
    }

    public NorbsShoppingMallException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
