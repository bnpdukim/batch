package com.bnpinnovation.batch.domain;

import com.bnpinnovation.batch.type.Grade;
import com.bnpinnovation.batch.type.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;
    private String password;
    private String email;
    private String principal;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    public User(String name, String password, String email, String principal, UserStatus status, LocalDateTime createdDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = createdDate;
    }

    public User inactive() {
        status = UserStatus.INACTIVE;
        return this;
    }
}
