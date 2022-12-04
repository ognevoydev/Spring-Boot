package com.ognevoydev.quisell.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends HttpStatusCodeException {

    public static final String MESSAGE_TEMPLATE = "%s with id %s not found.";

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
