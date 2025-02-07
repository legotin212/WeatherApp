package com.repository;

import com.entity.User;
import com.entity.UserSession;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Repository
public class SessionRepository extends CrudRepository<UserSession, UUID> {
    private final SessionFactory sessionFactory;

    @Autowired
    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory, UserSession.class);
        this.sessionFactory = sessionFactory;
    }

    public Optional<UserSession> findActiveSessionByUser(User user) {
        LocalDateTime currentTime = LocalDateTime.now();
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM UserSession us WHERE us.user = :user AND us.ExpiresAt > :currentTime";
            Query<UserSession> query = session.createQuery(hql, UserSession.class);
            query.setParameter("user", user);
            query.setParameter("currentTime", currentTime);
            return Optional.ofNullable(query.uniqueResult());
        }
    }
    @Transactional
    public void deleteExpiredSessions() {
        LocalDateTime currentTime = LocalDateTime.now();
        try (Session session = sessionFactory.openSession()) {
            String hql = "DELETE FROM UserSession us WHERE us.ExpiresAt <= :currentTime";
            Query query = session.createQuery(hql);
            query.setParameter("currentTime", currentTime);
        }
    }
}