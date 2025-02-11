package com.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserSession {
    @Id
    @Column(name = "ID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

    public UserSession(User user, LocalDateTime expiresAt) {
        this.user = user;
        expiresAt = expiresAt;
    }
}
