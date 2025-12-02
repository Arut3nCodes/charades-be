package com.example.koornikbe.controller;

import com.example.koornikbe.model.Player;
import com.example.koornikbe.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService service;

    @GetMapping
    public List<Player> getAll() {
        return service.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Long id) {
        return service.getPlayerById(id);
    }

    @PostMapping
    public ResponseEntity<Player> create(@RequestBody Player body) {
        Player created = service.addPlayer(body);
        return ResponseEntity.created(URI.create("/api/players/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Player update(@PathVariable Long id, @RequestBody Player body) {
        return service.updatePlayer(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deletePlayer(id);
    }
}
