package com.sparta_express.ai.common.auditing;

import com.sparta_express.ai.common.entity.SoftDeleteEntity;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;

@RequiredArgsConstructor
public class SoftDeleteEntityListener {

    private final AuditorAware<String> auditorAware;

    // Entity가 update 되었을 때
    @PreUpdate
    public void softDeleteHandle(SoftDeleteEntity softDeleteEntity) {

        // isDeleted가 True면 deletedAt, deletedBy 설정
        if (softDeleteEntity.getIsDeleted() == Boolean.TRUE
            && softDeleteEntity.getDeletedAt() == null) {

            auditorAware.getCurrentAuditor().ifPresent(softDeleteEntity::setDeletedBy);
            softDeleteEntity.setDeletedAt(LocalDateTime.now());
        }
    }
}
