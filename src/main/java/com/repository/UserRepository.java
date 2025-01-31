package com.repository;

import com.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends CrudRepository<User, Integer> {
    private final SessionFactory sessionFactory;
    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
        this.sessionFactory = sessionFactory;
    }

    public User findByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
        }
    }

    public boolean existsByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class)
                    .setParameter("login", login)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }
}
