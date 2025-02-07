package com.service;

import com.dto.UserCredentialsDto;
import com.entity.User;
import com.entity.UserSession;

import java.util.Optional;

public interface UserAuthService {
    public void createUser(UserCredentialsDto credentials);
    public Optional<UserSession> login(UserCredentialsDto credentials);
    public void logout();
}
