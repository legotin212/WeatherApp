package com.scheduller;

import com.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SessionCleanupScheduler {
    private final int CLEANUP_INTERVAL_MILLISECONDS = 600000;
    private final UserSessionRepository sessionRepository;

    @Scheduled(fixedRate = CLEANUP_INTERVAL_MILLISECONDS)
    public void cleanupExpiredSessions() {
        sessionRepository.deleteExpiredSessions(LocalDateTime.now());
     }
}
