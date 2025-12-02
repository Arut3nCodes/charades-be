package com.example.koornikbe.controller;

import com.example.koornikbe.model.Round;
import com.example.koornikbe.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rounds")
@RequiredArgsConstructor
public class RoundController {
    private final RoundService service;

    @GetMapping
    public List<Round> getAll() {
        return service.getAllRounds();
    }

    @GetMapping("/{id}")
    public Round getById(@PathVariable Long id) {
        return service.getRoundById(id);
    }

    @PostMapping
    public ResponseEntity<Round> create(@RequestBody Round body) {
        Round created = service.addRound(body);
        return ResponseEntity.created(URI.create("/api/rounds/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Round update(@PathVariable Long id, @RequestBody Round body) {
        return service.updateRound(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteRound(id);
    }
}
