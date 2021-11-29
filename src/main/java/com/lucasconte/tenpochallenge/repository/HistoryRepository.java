package com.lucasconte.tenpochallenge.repository;

import com.lucasconte.tenpochallenge.entity.HistoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryRepository extends PagingAndSortingRepository<HistoryEntity, Long> {
}
