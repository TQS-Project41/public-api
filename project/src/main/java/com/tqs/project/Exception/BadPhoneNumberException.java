package com.tqs.project.Exception;

public class BadPhoneNumberException extends Exception {
    private static final long serialVersionUID = 1L;

    public BadPhoneNumberException(String message){
        super(message);
    }
}
