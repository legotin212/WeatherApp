package com.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
@Repository
public class SessionRepository extends CrudRepository<Session, UUID> {
    private final SessionFactory sessionFactory;
    @Autowired
    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Session.class);
        this.sessionFactory = sessionFactory;
    }

    public List<Session> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Session WHERE user.id = :userId", Session.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public List<Session> findActiveSessionsByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Session WHERE user.id = :userId AND ExpiresAt > :currentTime",
                            Session.class
                    )
                    .setParameter("userId", userId)
                    .setParameter("currentTime", new Timestamp(System.currentTimeMillis()))
                    .list();
        }
    }

    public void deleteAllByUserId(int userId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Session WHERE user.id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteExpiredSessions() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Session WHERE ExpiresAt <= :currentTime")
                    .setParameter("currentTime", new Timestamp(System.currentTimeMillis()))
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}