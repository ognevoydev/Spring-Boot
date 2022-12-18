package com.ognevoydev.quisell.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class HttpStatusException extends RuntimeException {

    private HttpStatus httpStatus = BAD_REQUEST;

    public HttpStatusException(String message) {
        super(message);
    }

    public HttpStatusException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }



    public HttpStatusException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

}