package com.example.koornikbe.repository;

import com.example.koornikbe.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
