package com.lucasconte.tenpochallenge.actuator;

import com.lucasconte.tenpochallenge.entity.HistoryEntity;
import com.lucasconte.tenpochallenge.repository.HistoryRepository;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class CustomTraceRepository implements HttpTraceRepository {
    private final HistoryRepository historyRepository;

    public CustomTraceRepository(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public List<HttpTrace> findAll() {
        return historyRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("cratedAt"))))
                .stream()
                .map(HistoryEntity::getHistory)
                .collect(Collectors.toList());
    }

    @Override
    public void add(HttpTrace trace) {
        var entity = new HistoryEntity();
        entity.setHistory(trace);
        historyRepository.save(entity);
    }
}
