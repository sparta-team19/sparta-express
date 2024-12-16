package com.sparta_express.ai.ais;

import com.querydsl.core.types.Predicate;
import com.sparta_express.ai.core.Ai;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {

    Page<Ai> findAll(Predicate predicate, Pageable pageable);
}
