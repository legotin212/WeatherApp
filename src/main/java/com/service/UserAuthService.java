package com.service;

import com.dto.UserLoginDto;
import com.entity.UserSession;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthService {
     void createUser(UserLoginDto credentials);
     Optional<UserSession> login(UserLoginDto credentials);
     void logout(UUID sessionId);

}
