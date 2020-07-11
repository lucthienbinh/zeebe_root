package com.mainapplication.spring_boot.base_entity_module.configuration;

import com.mainapplication.spring_boot.base_entity_module.service.DateTimeService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider",auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = {
        "com.mainapplication.spring_boot.base_entity_module.model.BaseEntity"
})
@EnableTransactionManagement
public class JpaAuditingConfiguration {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }
    // Persistence Context is an environment or cache where entity instances(which are capable 
    // of holding data and thereby having the ability to be persisted in a database) are managed by Entity Manager.
    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }
}