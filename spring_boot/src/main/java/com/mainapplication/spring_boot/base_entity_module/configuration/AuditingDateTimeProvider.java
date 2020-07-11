package com.mainapplication.spring_boot.base_entity_module.configuration;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import com.mainapplication.spring_boot.base_entity_module.service.DateTimeService;

import org.springframework.data.auditing.DateTimeProvider;

public class AuditingDateTimeProvider implements DateTimeProvider {
    private final DateTimeService dateTimeService;

    public AuditingDateTimeProvider(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @Override
    public Optional<TemporalAccessor> getNow() {
        // Create current dateTimeService
        return Optional.of(dateTimeService.getCurrentDateAndTime());
    }
}
