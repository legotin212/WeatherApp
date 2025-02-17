package com.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LocationAlreadyExistsException extends RuntimeException{
    private final String message;
}
