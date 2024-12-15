package com.sparta_express.ai.ais;

import com.sparta_express.ai.core.Ai;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {

}
