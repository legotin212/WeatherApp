package repository;

import entity.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class LocationRepository extends CrudRepository<Location, Integer> {
    private final SessionFactory sessionFactory;

    public LocationRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Location.class);
        this.sessionFactory = sessionFactory;
    }

    public List<Location> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Location WHERE name = :name", Location.class)
                    .setParameter("name", name)
                    .list();
        }
    }


    public List<Location> findByUserId(int userId) {
        try (Session session  = sessionFactory.openSession()) {
            return session.createQuery("FROM Location WHERE user.id = :userId", Location.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }
}
