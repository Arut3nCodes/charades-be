package com.example.koornikbe.repository;

import com.example.koornikbe.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
