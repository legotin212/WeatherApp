package com.service;

import com.dto.LocationDto;
import com.dto.WeatherDto;

import java.math.BigDecimal;
import java.util.List;

public interface WeatherApiClientService {
    List<LocationDto> getLocationsByName(String location);
    WeatherDto getWeatherForLocationByCoordinates(BigDecimal latitude, BigDecimal longitude);
}
