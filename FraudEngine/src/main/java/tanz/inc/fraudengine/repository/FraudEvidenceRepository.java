package tanz.inc.fraudengine.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import tanz.inc.fraudengine.entity.FraudEvidence;

@Repository
public interface FraudEvidenceRepository extends ReactiveMongoRepository<FraudEvidence, String> {
}