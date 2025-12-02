package com.example.koornikbe.service;

import com.example.koornikbe.model.Round;
import com.example.koornikbe.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {
    private final RoundRepository repository;

    public List<Round> getAllRounds() {
        return repository.findAll();
    }

    public Round getRoundById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Round not found: " + id));
    }

    public Round addRound(Round entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    public Round updateRound(Long id, Round entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Round not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void deleteRound(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Round not found: " + id);
        }
        repository.deleteById(id);
    }
}
