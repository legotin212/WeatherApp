package com.service;

import com.dto.LocationDto;
import com.dto.WeatherDto;
import com.entity.Location;

import java.util.List;

public interface WeatherApiClientService {
    List<LocationDto> getLocationsByName(String location);
    WeatherDto getWeatherForLocation(Location location);
}
