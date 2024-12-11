package com.sparta_express.auth.common.auditing;

import com.sparta_express.auth.common.entity.SoftDeleteEntity;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;

@RequiredArgsConstructor
public class SoftDeleteEntityListener {

    private final AuditorAware<String> auditorAware;

    public void softDeleteHandle(SoftDeleteEntity softDeleteEntity) {

        if (softDeleteEntity.getIsDeleted() == Boolean.FALSE
            && softDeleteEntity.getDeletedAt() == null) {

            auditorAware.getCurrentAuditor().ifPresent(softDeleteEntity::setDeletedBy);
            softDeleteEntity.setDeletedAt(LocalDateTime.now());
        }
    }
}
