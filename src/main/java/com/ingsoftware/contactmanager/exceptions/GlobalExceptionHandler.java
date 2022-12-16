package com.ingsoftware.contactmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorMessage userNotFoundException(UserNotFoundException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(InvalidPasswordException.class)
    public ErrorMessage invalidPasswordException(InvalidPasswordException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(InvalidEmailException.class)
    public ErrorMessage invalidEmailException(InvalidEmailException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ContactNotFoundException.class)
    public ErrorMessage contactNotFound(ContactNotFoundException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(EmailTakenException.class)
    public ErrorMessage emailTakenException(EmailTakenException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(ContactTypeNotFound.class)
    public ErrorMessage contactTypeNotFound(ContactTypeNotFound e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(ContactTypeExistsException.class)
    public ErrorMessage contactTypeExistsException(ContactTypeExistsException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(ActionNotAllowedException.class)
    public ErrorMessage actionNotAllowedException(ActionNotAllowedException e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationError(MethodArgumentNotValidException e) {
        Map<String, String> violations = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            violations.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return violations;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleUnexpectedError(Exception e) {
        return new ErrorMessage(e.getLocalizedMessage());
    }

    private record ErrorMessage(String message) {
    }


}
