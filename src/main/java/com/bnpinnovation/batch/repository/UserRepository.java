package com.bnpinnovation.batch.repository;

import com.bnpinnovation.batch.domain.User;
import com.bnpinnovation.batch.type.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUpdatedDateBeforeAndStatusEquals(LocalDateTime pastDate, UserStatus status);
}
