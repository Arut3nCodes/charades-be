package com.example.koornikbe.repository;

import com.example.koornikbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
