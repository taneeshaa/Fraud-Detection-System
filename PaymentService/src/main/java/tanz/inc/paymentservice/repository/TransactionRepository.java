package tanz.inc.paymentservice.repository;

import tanz.inc.paymentservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    // Free methods: save(), findById(), findAll()
}
