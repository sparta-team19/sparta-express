package com.sparta_express.ai.slacks;

import com.sparta_express.ai.core.Slack;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SlackRepository extends JpaRepository<Slack, UUID>,
    QuerydslPredicateExecutor<Slack> {

}
