package com.luizcasagrande.storeapi.config;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.NoResultException;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ResponseBody
    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(NOT_FOUND)
    public Error handleNoResultException(NoResultException e, WebRequest request) {
        return new Error(e.getMessage(), request.getDescription(false));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + SPACE + f.getDefaultMessage())
                .collect(joining("; "));
        return new Error(message, request.getDescription(false));
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Error handleException(Exception e, WebRequest request) {
        return new Error(e.getMessage(), request.getDescription(false));
    }

    record Error(String message, String path) {
    }
}
