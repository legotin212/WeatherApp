package util.hibernateutil;

import entity.Location;
import entity.User;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.FlywayMigration;
@UtilityClass
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        FlywayMigration.runMigrations();
        try {
            Configuration configuration = new Configuration();
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

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Hibernate ", e);
        }
    }

}
