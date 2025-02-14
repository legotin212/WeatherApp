package com.service;

import com.dto.LocationDto;
import com.dto.WeatherDto;
import com.exception.WeatherApiException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WeatherApiRequestClientService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("API_KEY");
    private final WebClient webClient;
    @Autowired
    public WeatherApiRequestClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<WeatherDto> getWeatherForLocationByCoordinates(BigDecimal lat, BigDecimal lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", API_KEY)
                        .queryParam("units", "metric")
                        .queryParam("lang", "ru")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleErrors)
                .bodyToMono(WeatherDto.class);
    }

    public Mono<List<LocationDto>> getLocationsByName(String locationName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo/1.0/direct")
                        .queryParam("q", locationName)
                        .queryParam("limit", 5)
                        .queryParam("appid", API_KEY)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleErrors)
                .bodyToMono(new ParameterizedTypeReference<List<LocationDto>>() {});
    }

    private Mono<? extends Throwable> handleErrors(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new WeatherApiException(response.statusCode().value(), errorBody)));
    }

}
