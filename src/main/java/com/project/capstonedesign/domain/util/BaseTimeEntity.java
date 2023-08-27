package com.project.capstonedesign.domain.util;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = createdDate;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
