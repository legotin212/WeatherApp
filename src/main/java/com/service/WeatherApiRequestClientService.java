package com.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherApiRequestClientService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("API_KEY");
    private final WebClient webClient;
    @Autowired
    public WeatherApiRequestClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getWeatherForLocationByCoordinates(double lat, double lon) {
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
                .bodyToMono(String.class);
    }
    public Mono<String> getWeatherForLocationByName(String locationName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("q",locationName)
                        .queryParam("appid",API_KEY)
                        .queryParam("units","metric")
                        .queryParam("lang","ru")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

}
