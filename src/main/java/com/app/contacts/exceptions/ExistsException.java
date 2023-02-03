package com.app.contacts.exceptions;

public class ExistsException extends RuntimeException {
    public ExistsException(String message) {
        super(message);
    }
}