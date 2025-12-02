package com.example.koornikbe.controller;

import com.example.koornikbe.model.Game;
import com.example.koornikbe.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService service;

    @GetMapping
    public List<Game> getAll() {
        return service.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getById(@PathVariable Long id) {
        return service.getGameById(id);
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game body) {
        Game created = service.addGame(body);
        return ResponseEntity.created(URI.create("/api/games/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Game update(@PathVariable Long id, @RequestBody Game body) {
        return service.updateGame(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteGame(id);
    }
}
