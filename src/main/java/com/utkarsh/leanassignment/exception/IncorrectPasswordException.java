package com.utkarsh.leanassignment.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class IncorrectPasswordException extends BadCredentialsException {
    public IncorrectPasswordException(String incorrect_password) {
        super(incorrect_password);
    }

}