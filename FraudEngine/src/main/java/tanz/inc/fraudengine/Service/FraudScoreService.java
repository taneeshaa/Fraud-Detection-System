package tanz.inc.fraudengine.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tanz.inc.fraudengine.dto.FraudResult;
import tanz.inc.fraudengine.entity.FraudEvidence;
import tanz.inc.fraudengine.repository.FraudEvidenceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FraudScoreService {
    private final VelocityService velocityService;
    private final FraudEvidenceRepository evidenceRepository;


    public Mono<FraudResult> processCheck(String userId, BigDecimal amount) {
        return Mono.zip(
                velocityService.getTransactionCount(userId),
                Mono.just(amount.compareTo(new BigDecimal("10000")) > 0)
        ).flatMap(tuple -> {
            long count = tuple.getT1();
            boolean isHighAmount = tuple.getT2();

            int score = 0;
            if (count > 5) score += 40; // Too many transactions
            if (isHighAmount) score += 50; // Suspect amount

            String status = (score >= 50) ? "FLAGGED" : "CLEAN";

            // Save evidence to MongoDB (Async) and return result
            return evidenceRepository.save(new FraudEvidence(null, userId, score, status, LocalDateTime.now()))
                    .thenReturn(new FraudResult(score, status));
        });
    }
}
