package com.twinkles.Norbs_Shopping_Mall.web.exception.handlers;

import com.twinkles.Norbs_Shopping_Mall.web.exception.ProductAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NorbsShoppingMallExceptionHandler {

    @ExceptionHandler({ProductAlreadyExistException.class})
    public ResponseEntity<Object> handleProductAlreadyExistException(ProductAlreadyExistException ex){
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
