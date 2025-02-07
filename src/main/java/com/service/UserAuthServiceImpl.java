package com.service;

import com.dto.UserCredentialsDto;
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
    public void createUser(UserCredentialsDto credentials) {
      String hashPassword = PasswordUtil.hashPassword(credentials.password());
      userRepository.save(new User(credentials.name(), hashPassword));
    }

    @Override
    public Optional<UserSession> login(UserCredentialsDto credentials) {
        Optional<User> user = userRepository.findByLogin(credentials.name());
        if(user.isEmpty()) {
            return Optional.empty();
        }
        if(!PasswordUtil.matches(credentials.password(), user.get().getPassword())){
            return Optional.empty();
        }
        UserSession userSession = getSession(user.get());
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
