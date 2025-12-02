package com.example.koornikbe.service;

import com.example.koornikbe.model.Game;
import com.example.koornikbe.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository repository;

    public List<Game> getAllGames() {
        return repository.findAll();
    }

    public Game getGameById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found: " + id));
    }

    public Game addGame(Game entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    public Game updateGame(Long id, Game entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void deleteGame(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found: " + id);
        }
        repository.deleteById(id);
    }
}
