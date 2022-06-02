package com.tqs.project.Exception;

public class UserAlreadyAssignedException extends Exception{
    private static final long serialVersionUID = 1L;

    public UserAlreadyAssignedException(String message){
        super(message);
    }
}
