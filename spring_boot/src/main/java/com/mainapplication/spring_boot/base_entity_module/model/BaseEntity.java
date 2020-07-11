package com.mainapplication.spring_boot.base_entity_module.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "creation_time", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "update_time", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "created_by_user")
    @CreatedBy
    private String createdByUser;

    @Column(name = "update_by_user")
    @LastModifiedBy
    private String modifiedByUser;
    
}
