package com.service;

import com.dto.LocationDeleteRequest;
import com.dto.LocationDto;
import com.dto.WeatherDto;
import com.entity.Location;
import com.entity.User;
import com.exception.LocationDoesNotExistsException;
import com.mapper.LocationMapper;
import com.repository.LocationRepository;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
    private final WeatherApiClientService weatherApiClient;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final UserRepository userRepository;


    @Override
    public WeatherDto getWeatherForLocation(Location location) {
      WeatherDto weatherDto = weatherApiClient.getWeatherForLocation(location);
      weatherDto.getCoord().setLat(location.getLatitude());
      weatherDto.getCoord().setLon(location.getLongitude());
      weatherDto.setName(location.getName());
      return weatherDto;
    }

    @Override
    public List<WeatherDto> getWeatherList(User user) {
        List<WeatherDto> weatherList = new ArrayList<>();
        user.getLocations().forEach(location -> weatherList.add(getWeatherForLocation(location)));
        return weatherList;
    }

    @Override
    public void saveLocation(User user, LocationDto locationDto) {
        Location location = locationMapper.locationDtoToLocation(locationDto);
        location.setUser(user);
        locationRepository.save(location);
    }

    @Override
    @Transactional
    public void removeLocation(User user,  LocationDeleteRequest locationDeleteRequest) {
        Optional<Location> location = locationRepository.findByUserIdAndLatitudeAndLongitude(user.getId(),locationDeleteRequest.getLat(),locationDeleteRequest.getLon());
        if(location.isEmpty()){
           throw new LocationDoesNotExistsException("No location with this name, lat, lon were found");
        }
        Location locationToDelete = location.get();
        locationRepository.deleteById(locationToDelete.getId());
    }

    @Override
    public List<LocationDto> getLocationsByName(String locationName) {
        return weatherApiClient.getLocationsByName(locationName);
    }
}
