package tanz.inc.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tanz.inc.paymentservice.dto.PaymentRequest;
import tanz.inc.paymentservice.entity.Transaction;
import tanz.inc.paymentservice.repository.TransactionRepository;
import tanz.inc.paymentservice.service.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final TransactionRepository repository;

    @PostMapping
    @PreAuthorize("hasAuthority('APPROLE_Payment.Create')")
    public Mono<ResponseEntity<Transaction>> createPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.createPayment(request)
                .map(savedTxn -> ResponseEntity.status(HttpStatus.CREATED).body(savedTxn));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getPayment(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}