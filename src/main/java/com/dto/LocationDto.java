package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем неизвестные поля, включая local_names
public class LocationDto {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
    private String state; // Добавлено поле "state"
}