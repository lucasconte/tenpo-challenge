package com.lucasconte.tenpochallenge.controller;

import com.lucasconte.tenpochallenge.repository.HistoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryController {
    private final HistoryRepository historyRepository;

    public HistoryController(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("/history")
    public ResponseEntity history(Pageable pageable) {
        return ResponseEntity.ok(historyRepository.findAll(pageable));
    }
}
