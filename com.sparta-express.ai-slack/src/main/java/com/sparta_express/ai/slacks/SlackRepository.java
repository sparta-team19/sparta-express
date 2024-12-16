package com.sparta_express.ai.slacks;

import com.sparta_express.ai.core.Slack;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackRepository extends JpaRepository<Slack, UUID> {

}
