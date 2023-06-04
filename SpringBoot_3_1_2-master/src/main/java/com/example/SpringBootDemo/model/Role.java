package com.example.SpringBootDemo.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление ролей пользователей приложения
 */
public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
