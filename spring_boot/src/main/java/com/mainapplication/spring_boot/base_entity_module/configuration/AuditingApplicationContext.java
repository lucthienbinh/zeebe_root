package com.mainapplication.spring_boot.base_entity_module.configuration;

import com.mainapplication.spring_boot.base_entity_module.service.CurrentTimeDateTimeService;
import com.mainapplication.spring_boot.base_entity_module.service.DateTimeService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.mainapplication.spring_boot.base_entity_module.model")
@Import({JpaAuditingConfiguration.class})
public class AuditingApplicationContext {
 
    // Declare a bean
    @Bean
    DateTimeService currentTimeDateTimeService() {
        return new CurrentTimeDateTimeService();
    }
}

