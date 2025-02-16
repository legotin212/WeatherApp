package com.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidUserSession extends RuntimeException{
    @Getter
    private final String message;
}
