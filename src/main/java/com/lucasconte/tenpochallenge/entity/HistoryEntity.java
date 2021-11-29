package com.lucasconte.tenpochallenge.entity;

import com.lucasconte.tenpochallenge.entity.converter.HttpTraceToStringConverter;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@EntityListeners(AuditingEntityListener.class)
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = HttpTraceToStringConverter.class)
    @Column(length = 2024)
    private HttpTrace history;

    @CreatedDate
    private LocalDateTime createdAt;

    public HttpTrace getHistory() {
        return history;
    }

    public void setHistory(HttpTrace history) {
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
