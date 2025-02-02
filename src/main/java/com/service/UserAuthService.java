package com.service;

import com.entity.User;
import com.entity.UserSession;

import java.util.Optional;

public interface UserAuthService {
    public void createUser(User user);
    public Optional<UserSession> login(User user);
    public void logout();
}
