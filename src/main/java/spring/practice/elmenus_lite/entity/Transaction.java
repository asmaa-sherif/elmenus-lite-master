package spring.practice.elmenus_lite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "transaction_details_id")
    private TransactionDetails transactionDetails;

    @ManyToOne
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private Double amount;
    private LocalDateTime transactionDate;
}


