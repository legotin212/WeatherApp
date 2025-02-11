package com.scheduller;

import com.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionCleanupScheduler {
    private final UserSessionRepository sessionRepository;
    @Autowired
    public SessionCleanupScheduler(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    @Scheduled(fixedRate = 600000)
    public void cleanupExpiredSessions() {
        sessionRepository.deleteExpiredSessions(LocalDateTime.now());
     }
}
