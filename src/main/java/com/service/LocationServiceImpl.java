package com.service;

import com.dto.LocationDto;
import com.dto.WeatherDto;
import com.entity.Location;
import com.entity.User;
import com.mapper.LocationMapper;
import com.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService{
    private final WeatherApiClientService weatherApiRequestClientService;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    @Autowired
    public LocationServiceImpl(WeatherApiClientService weatherApiRequestClientService, LocationRepository locationRepository, LocationMapper locationMapper) {
        this.weatherApiRequestClientService = weatherApiRequestClientService;
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public WeatherDto getWeatherForLocation(Location location) {
        return null;
    }

    @Override
    public void saveLocation(User user, LocationDto locationDto) {
        Location location = locationMapper.locationDtoToLocation(locationDto);
        location.setUser(user);
        locationRepository.save(location);
    }

    @Override
    public void deleteLocation(User user, Location location) {
        locationRepository.delete(location);
    }

    @Override
    public List<LocationDto> getLocationsByName(String locationName) {
       List<LocationDto> locationDtos =  weatherApiRequestClientService.getLocationsByName(locationName);
       return locationDtos;
    }
}
