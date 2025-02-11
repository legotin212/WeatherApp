package com.exception;

import lombok.Setter;

@Setter
public class UnauthorizedException extends RuntimeException{
    private String message;

    public UnauthorizedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
