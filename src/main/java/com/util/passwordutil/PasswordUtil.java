package com.util.passwordutil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Хеширование пароля
    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Проверка соответствия пароля
    public static boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}