package com.ognevoydev.quisell.common.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends HttpStatusException{

    private static final String MESSAGE_TEMPLATE = "%s with id %s not found";

    public NotFoundException(String message) {
        super(message, NOT_FOUND);
    }

    public NotFoundException(UUID id) {
        super(MESSAGE_TEMPLATE.formatted("Entity", id), NOT_FOUND);
    }

    public NotFoundException(UUID id, Class<?> clazz) {
        super(MESSAGE_TEMPLATE.formatted(getEntityName(clazz), id), NOT_FOUND);
    }

    private static String getEntityName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

}
