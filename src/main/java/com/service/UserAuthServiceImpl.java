package com.service;

import com.entity.UserSession;
import com.entity.User;
import com.repository.SessionRepository;
import com.repository.UserRepository;
import com.util.passwordutil.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final int SESSION_LIFETIME_SECONDS = 86400;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserAuthServiceImpl(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
      String hashPassword = PasswordUtil.hashPassword(user.getPassword());
      user.setPassword(hashPassword);
      createSession(user);
    }

    private void createSession(User user){
        UserSession session = new UserSession();
        session.setUser(user);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(SESSION_LIFETIME_SECONDS);
        session.setExpiresAt(expires);
        sessionRepository.save(session);

    }
}
