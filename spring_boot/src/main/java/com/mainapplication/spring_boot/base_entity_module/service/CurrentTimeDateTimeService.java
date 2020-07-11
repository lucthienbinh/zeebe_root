package com.mainapplication.spring_boot.base_entity_module.service;

import java.time.LocalDateTime;

public class CurrentTimeDateTimeService implements DateTimeService {
    @Override
    public LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now();
    }
}