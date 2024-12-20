package model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TransactionDetails")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "transactionId", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "lotId", nullable = false)
    private Lot lot;

    @Column(nullable = false)
    private double quantity;
}


