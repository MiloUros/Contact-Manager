package com.ingsoftware.contactmanager.exceptions;

public class ContactTypeExistsException extends RuntimeException {

    public ContactTypeExistsException(String message) {
        super(message);
    }

    public ContactTypeExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

