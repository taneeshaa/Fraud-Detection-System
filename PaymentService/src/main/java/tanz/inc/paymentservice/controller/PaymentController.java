package tanz.inc.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tanz.inc.paymentservice.dto.PaymentRequest;
import tanz.inc.paymentservice.entity.Transaction;
import tanz.inc.paymentservice.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final TransactionRepository repository;

    @PostMapping
    public ResponseEntity<Transaction> createPayment(@Valid @RequestBody PaymentRequest request) {
        Transaction txn = new Transaction();
        txn.setUserId(request.userId());
        txn.setAmount(request.amount());
        txn.setStatus("PENDING");
        txn.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(txn));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getPayment(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}