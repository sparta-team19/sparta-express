package com.sparta_express.company_product.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(nullable = false)
    private boolean isDelete = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String updatedBy;

    private LocalDateTime deletedAt;

    private String deletedBy;

    @PrePersist
    public void prePersist() {
        createdBy = "John";
    }

    public void delete() {
        isDelete = true;
    }

}
