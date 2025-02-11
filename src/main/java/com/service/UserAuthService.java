package com.service;

import com.dto.UserLoginDto;
import com.entity.UserSession;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthService {
    public void createUser(UserLoginDto credentials);
    public Optional<UserSession> login(UserLoginDto credentials);
    public void logout(UUID sessionId);
}
