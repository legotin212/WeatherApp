package com.exception;

import lombok.Getter;

@Getter
public class WeatherApiException extends RuntimeException {
    private final String responseBody;

    public WeatherApiException(String responseBody) {
        super("Ошибка API погоды: "  + responseBody);
        this.responseBody = responseBody;
    }

}