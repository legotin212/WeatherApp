package com.service;

import com.entity.User;
import com.entity.UserSession;
import com.repository.SessionRepository;
import com.repository.UserRepository;
import com.util.passwordutil.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

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
      userRepository.save(user);
    }

    @Override
    public Optional<UserSession> login(User user) {
        Optional<User> databaseCredentials = userRepository.findByLogin(user.getLogin());
        if(databaseCredentials.isEmpty()) {
            return Optional.empty();
        }
        if(!PasswordUtil.matches(user.getPassword(), databaseCredentials.get().getPassword())){
            return Optional.empty();
        }
        UserSession userSession = getSession(databaseCredentials.get());
        return Optional.of(userSession) ;
    }

    @Override
    public void logout() {

    }

    private UserSession getSession(User databaseCredentials) {
    Optional<UserSession> session = sessionRepository.findActiveSessionByUser(databaseCredentials);
        return session.orElseGet(() -> createSession(databaseCredentials));
    }

    private UserSession createSession(User user){
        UserSession session = new UserSession();
        session.setUser(user);
        LocalDateTime expires = LocalDateTime.now().plusSeconds(SESSION_LIFETIME_SECONDS);
        session.setExpiresAt(expires);
        sessionRepository.save(session);
        return session;
    }

}
