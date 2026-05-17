package tanz.inc.fraudengine.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tanz.inc.fraudengine.entity.FraudEvidence;

public interface FraudEvidenceRepository extends ReactiveMongoRepository<FraudEvidence, String> {
}