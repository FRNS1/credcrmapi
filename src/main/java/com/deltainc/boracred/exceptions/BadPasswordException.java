package com.deltainc.boracred.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException (String message){
        super(message);
    }
}