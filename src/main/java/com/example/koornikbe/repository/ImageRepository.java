package com.example.koornikbe.repository;

import com.example.koornikbe.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
