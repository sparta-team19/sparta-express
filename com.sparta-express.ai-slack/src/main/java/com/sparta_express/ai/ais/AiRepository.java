package com.sparta_express.ai.ais;

import com.querydsl.core.types.Predicate;
import com.sparta_express.ai.core.Ai;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AiRepository extends JpaRepository<Ai, UUID>,
    QuerydslPredicateExecutor<Ai> {

    Page<Ai> findAll(Predicate predicate, Pageable pageable);
}
