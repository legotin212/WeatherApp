package com.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${FLYWAY_USER}")
    private String flywayUser;

    @Value("${FLYWAY_PASSWORD}")
    private String flywayPassword;

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/postgres", flywayUser, flywayPassword)
                .load();

        flyway.migrate(); // Запуск миграций при старте
        return flyway;
    }
}