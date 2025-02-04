package com.config;

import com.entity.Location;
import com.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory sessionFactory() {

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "none");

        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.default_schema", "public");
        configuration.setProperty("hibernate.connection.username", System.getenv("FLYWAY_USER"));
        configuration.setProperty("hibernate.connection.password", System.getenv("FLYWAY_PASSWORD"));

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Location.class);
        configuration.addAnnotatedClass(Session.class);

        return configuration.buildSessionFactory();
    }
}
