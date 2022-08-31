package com.twinkles.Norbs_Shopping_Mall.web.exception.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.*;
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
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException){
        return new ResponseEntity<>(userAlreadyExistException.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NorbsShoppingMallException.class)
    public ResponseEntity<String> handleNorbsShoppingMallException(NorbsShoppingMallException norbsShoppingMallException){
        return new ResponseEntity<>(norbsShoppingMallException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<String> handleProductDoesNotExistException(ProductDoesNotExistException productDoesNotExistException){
        return new ResponseEntity<>(productDoesNotExistException.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<String> handleProductAlreadyExistException(ProductAlreadyExistException productAlreadyExistException){
        return new ResponseEntity<>(productAlreadyExistException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ioException){
        return new ResponseEntity<>(ioException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonPatchException.class)
    public ResponseEntity<String> handleJsonPatchException(JsonPatchException jsonPatchException){
        return new ResponseEntity<>(jsonPatchException.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException jsonProcessingException){
        return new ResponseEntity<>(jsonProcessingException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
