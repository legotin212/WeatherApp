package util.hibernateutil;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // Hibernate настройки
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "none"); // Flyway управляет схемой

            // Настройки подключения к базе данных
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
            configuration.setProperty("hibernate.connection.username", System.getenv("FLYWAY_USER")); // Извлечение имени пользователя из ENV
            configuration.setProperty("hibernate.connection.password", System.getenv("FLYWAY_PASSWORD")); // Извлечение пароля из ENV

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Hibernate", e);
        }
    }

}
