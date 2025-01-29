package util;

import org.flywaydb.core.Flyway;

public class FlywayMigration {
    public static void runMigrations() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "${FLYWAY_USER}", "${FLYWAY_PASSWORD}")
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }
}