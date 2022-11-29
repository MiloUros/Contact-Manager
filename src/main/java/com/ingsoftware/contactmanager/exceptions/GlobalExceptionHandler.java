package com.ingsoftware.contactmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorMessage userNotFoundException(UserNotFoundException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(InvalidPasswordException.class)
    public ErrorMessage invalidPasswordException(InvalidPasswordException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(InvalidEmailException.class)
    public ErrorMessage invalidEmailException(InvalidEmailException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ContactNotFoundException.class)
    public ErrorMessage contactNotFound(ContactNotFoundException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(EmailTakenException.class)
    public ErrorMessage emailTakenException(EmailTakenException e) {
        return createErrorMessage(e.getMessage());
    }

    private ErrorMessage createErrorMessage(String message) {
        return ErrorMessage.builder().errorMessage(message).build();
    }

}
