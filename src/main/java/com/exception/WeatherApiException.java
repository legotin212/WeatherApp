package com.exception;

import lombok.Getter;

@Getter
public class WeatherApiException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public WeatherApiException(int statusCode, String responseBody) {
        super("Ошибка API погоды: " + statusCode + " - " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

}