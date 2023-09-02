package com.ognevoydev.quisell.controller;

import com.ognevoydev.quisell.common.exception.HttpStatusException;
import com.ognevoydev.quisell.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        return handleException(exception, NOT_FOUND);
    }

    @ExceptionHandler(HttpStatusException.class)
    protected ResponseEntity<?> handleHttpStatusException(HttpStatusException exception) {
        return handleException(exception, exception.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        return handleException(exception, INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<?> handleException(Exception exception, HttpStatus status) {
        return handleException(exception, status, exception.getMessage());
    }

    private ResponseEntity<?> handleException(Throwable throwable, HttpStatus status, Object message) {
        if (log.isTraceEnabled()) log.trace("Exception captured globally", throwable);
        Map<String, ?> body = log.isDebugEnabled() ?
                Map.of("status", status.value(), "message", message, "error", status.getReasonPhrase(), "debug", getStackTrace(throwable)) :
                Map.of("status", status.value(), "message", message, "error", status.getReasonPhrase());
        return ResponseEntity.status(status).body(body);
    }

}
