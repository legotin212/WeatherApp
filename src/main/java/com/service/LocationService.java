package com.service;

import com.dto.LocationDto;
import com.dto.WeatherDto;
import com.entity.Location;
import com.entity.User;

import java.util.List;

public interface LocationService {
    WeatherDto getWeatherForLocation(Location location);

    void saveLocation(User user, LocationDto location);
    void deleteLocation(User user, Location location);
    List<LocationDto> getLocationsByName(String name);
}
