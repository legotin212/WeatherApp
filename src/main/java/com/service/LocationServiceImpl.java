package com.service;

import com.dto.request.LocationDeleteRequestDto;
import com.dto.response.LocationResponseDto;
import com.dto.response.WeatherResponseDto;
import com.entity.Location;
import com.entity.User;
import com.exception.LocationDoesNotExistsException;
import com.mapper.LocationMapper;
import com.repository.LocationRepository;
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

    @Override
    public WeatherResponseDto getWeatherForLocation(Location location) {
      WeatherResponseDto weatherDto = weatherApiClient.getWeatherForLocation(location);
      weatherDto.getCoord().setLat(location.getLatitude());
      weatherDto.getCoord().setLon(location.getLongitude());
      weatherDto.setName(location.getName());
      return weatherDto;
    }

    @Override
    public List<WeatherResponseDto> getWeatherList(User user) {
        List<WeatherResponseDto> weatherList = new ArrayList<>();
        user.getLocations().forEach(location -> weatherList.add(getWeatherForLocation(location)));
        return weatherList;
    }

    @Override
    public void saveLocation(User user, LocationResponseDto locationDto) {
        Location location = locationMapper.LocationResponseDtoToLocation(locationDto);
        location.setUser(user);
        locationRepository.save(location);
    }

    @Override
    @Transactional
    public void removeLocation(User user,  LocationDeleteRequestDto locationDeleteRequest) {
        Optional<Location> location = locationRepository.findByUserIdAndLatitudeAndLongitude(user.getId(),locationDeleteRequest.getLat(),locationDeleteRequest.getLon());
        if(location.isEmpty()){
           throw new LocationDoesNotExistsException("No location with this name, lat, lon were found");
        }
        Location locationToDelete = location.get();
        locationRepository.deleteById(locationToDelete.getId());
    }

    @Override
    public List<LocationResponseDto> getLocationsByName(String locationName) {
        return weatherApiClient.getLocationsByName(formatLocationName(locationName));
    }

    private String formatLocationName(String locationName) {
        return locationName.trim().replace(" ", "-");
    }
}
