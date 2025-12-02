package com.example.koornikbe.repository;

import com.example.koornikbe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
