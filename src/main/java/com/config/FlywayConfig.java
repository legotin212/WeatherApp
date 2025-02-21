package com.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class FlywayConfig {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String dbUser = dotenv.get("DB_USER");
    private static final String dbPassword = dotenv.get("DB_PASSWORD");

    @Bean
    @Profile("dev")
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/mydatabase", dbUser, dbPassword)
                .load();
        flyway.migrate();
        return flyway;
    }

}
