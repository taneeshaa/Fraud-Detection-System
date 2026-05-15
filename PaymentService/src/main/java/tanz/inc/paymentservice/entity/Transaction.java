package tanz.inc.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // Using UUID for distributed systems
    private String userId;
    private BigDecimal amount;
    private String status; // e.g., PENDING, SETTLED, BLOCKED
    private LocalDateTime createdAt;
}
