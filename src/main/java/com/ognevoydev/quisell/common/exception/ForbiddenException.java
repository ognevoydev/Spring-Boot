package com.ognevoydev.quisell.common.exception;

import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ForbiddenException extends HttpStatusException{

    private static final String MESSAGE_TEMPLATE = "Don't have permission to access %s with id %s";

    public ForbiddenException(String message) {
        super(message, FORBIDDEN);
    }

    public ForbiddenException(UUID id) {
        super(MESSAGE_TEMPLATE.formatted("Entity", id), FORBIDDEN);
    }

    public ForbiddenException(UUID id, Class<?> clazz) {
        super(MESSAGE_TEMPLATE.formatted(getEntityName(clazz), id), FORBIDDEN);
    }

    private static String getEntityName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

}
