package tanz.inc.fraudengine.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record FraudEvidence(
        @Id String id,
        String userId,
        int score,
        String decision,
        LocalDateTime timestamp
) {}
