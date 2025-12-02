package com.example.koornikbe.service;

import com.example.koornikbe.model.Prompt;
import com.example.koornikbe.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final PromptRepository repository;

    public List<Prompt> getAllPrompts() {
        return repository.findAll();
    }

    public Prompt getPromptById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prompt not found: " + id));
    }

    public Prompt addPrompt(Prompt entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    public Prompt updatePrompt(Long id, Prompt entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prompt not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void deletePrompt(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prompt not found: " + id);
        }
        repository.deleteById(id);
    }
}
