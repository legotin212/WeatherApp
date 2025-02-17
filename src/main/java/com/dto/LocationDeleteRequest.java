package com.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDeleteRequest {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;


}