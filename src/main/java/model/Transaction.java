package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private String date;

    private String customer;

    @ManyToOne
    @JoinColumn(name = "staff", nullable = false)
    private User staff;
}
