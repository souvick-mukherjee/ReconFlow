package com.reconflow.Idempotency.repository;

import com.reconflow.Idempotency.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedEventsRepository extends JpaRepository<ProcessedEvent, UUID> {
}
