package com.scheduller;

import com.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SessionCleanupScheduler {
    private final SessionRepository sessionRepository;
    @Autowired
    public SessionCleanupScheduler(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    @Scheduled(fixedRate = 600000)
    public void cleanupExpiredSessions() {
        sessionRepository.deleteExpiredSessions();
     }
}
