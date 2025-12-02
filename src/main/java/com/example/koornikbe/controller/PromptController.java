package com.example.koornikbe.controller;

import com.example.koornikbe.model.Prompt;
import com.example.koornikbe.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PromptController {
    private final PromptService service;

    @GetMapping
    public List<Prompt> getAll() {
        return service.getAllPrompts();
    }

    @GetMapping("/{id}")
    public Prompt getById(@PathVariable Long id) {
        return service.getPromptById(id);
    }

    @PostMapping
    public ResponseEntity<Prompt> create(@RequestBody Prompt body) {
        Prompt created = service.addPrompt(body);
        return ResponseEntity.created(URI.create("/api/prompts/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Prompt update(@PathVariable Long id, @RequestBody Prompt body) {
        return service.updatePrompt(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deletePrompt(id);
    }
}
