package com.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "Sessions")
@Getter
@Setter
@NoArgsConstructor
public class UserSession {
    @Id
    @Column(name = "ID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
    @Column(name = "ExpiresAt")
    private LocalDateTime ExpiresAt;

    public UserSession(User user, LocalDateTime expiresAt) {
        this.user = user;
        ExpiresAt = expiresAt;
    }
}
