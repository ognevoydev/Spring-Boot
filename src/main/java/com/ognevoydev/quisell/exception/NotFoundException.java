package com.ognevoydev.quisell.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends HttpStatusException{

    private static final HttpStatus exceptionStatus = NOT_FOUND;

    private static final String MESSAGE_TEMPLATE = "%s with id %s not found";

    public NotFoundException(String message) {
        super(message, exceptionStatus);
    }

    public NotFoundException(UUID id) {
        super(MESSAGE_TEMPLATE.formatted("Entity", id), exceptionStatus);
    }

    public NotFoundException(UUID id, Class<?> _class) {
        super(MESSAGE_TEMPLATE.formatted(getEntityName(_class), id), exceptionStatus);
    }

    private static String getEntityName(Class<?> _class) {
        return _class.getSimpleName();
    }

}
