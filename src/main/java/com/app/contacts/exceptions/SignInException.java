package com.app.contacts.exceptions;

public class SignInException extends RuntimeException {
    public SignInException(String message) {
        super(message);
    }
}