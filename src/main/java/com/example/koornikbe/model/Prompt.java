package com.example.koornikbe.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prompts")
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    // Rounds 0..N - 1 Prompts (inverse side)
    @OneToMany(mappedBy = "prompt", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Round> rounds = new ArrayList<>();
}
