package com.example.koornikbe.service;

import com.example.koornikbe.model.Player;
import com.example.koornikbe.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public Player getPlayerById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id));
    }

    public Player addPlayer(Player entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    public Player updatePlayer(Long id, Player entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void deletePlayer(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id);
        }
        repository.deleteById(id);
    }
}
