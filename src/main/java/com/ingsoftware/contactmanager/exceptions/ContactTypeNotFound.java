package com.ingsoftware.contactmanager.exceptions;

public class ContactTypeNotFound extends RuntimeException {

    public ContactTypeNotFound(String message) {
        super(message);
    }

    public ContactTypeNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}

