package tanz.inc.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tanz.inc.paymentservice.dto.FraudResult;
import tanz.inc.paymentservice.dto.PaymentRequest;
import tanz.inc.paymentservice.entity.Transaction;
import tanz.inc.paymentservice.repository.TransactionRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final TransactionRepository repository;
    private final WebClient fraudWebClient;

    public Mono<Transaction> createPayment(PaymentRequest request) {
        // Create and save initial transaction
        Transaction txn = new Transaction();
        txn.setUserId(request.userId());
        txn.setAmount(request.amount());
        txn.setStatus("PENDING");
        txn.setCreatedAt(LocalDateTime.now());

        return Mono.fromCallable(() -> repository.save(txn))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(savedTxn ->
                        fraudWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                                .path("/fraud-check")
                                                .queryParam("userId", request.userId())
                                                .queryParam("amount", request.amount())
                                                .build())
                                        .retrieve()
                                        .bodyToMono(FraudResult.class)
                                        .flatMap(result -> {
                                            savedTxn.setStatus(result.status());
                                            return Mono.fromCallable(() -> repository.save(savedTxn))
                                                    .subscribeOn(Schedulers.boundedElastic());
                                        })
                );
//        Transaction savedTxn = repository.save(txn);
//
//        // Check fraud status
//        FraudResult result = fraudWebClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/fraud-check")
//                        .queryParam("userId", request.userId())
//                        .queryParam("amount", request.amount())
//                        .build())
//                .retrieve()
//                .bodyToMono(FraudResult.class)
//                .block();
//
//        // Update transaction with fraud check result
//        savedTxn.setStatus(result.status());
//        return repository.save(savedTxn);
    }
}

