package com.service;

import com.dto.LocationDto;
import com.exception.InvalidApiRequestException;
import com.exception.JsonDeserializationException;
import com.exception.WeatherApiException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class WeatherApiRequestClientService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("API_KEY");


    public List<LocationDto> getLocationsByName(String location) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/geo/1.0/direct")
                .queryParam("q", location)
                .queryParam("limit", 5)
                .queryParam("appid", API_KEY)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<LocationDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<LocationDto>>() {}
            );

            return response.getBody();
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException) {
                throw new InvalidApiRequestException("invalid request");
            } else if (e instanceof HttpServerErrorException) {
                throw new WeatherApiException("Weather server not working");
            } else {
                throw new JsonDeserializationException("Error fetching location data" );
            }
        }
    }
}
