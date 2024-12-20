package model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TransactionDetails")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetailID")
    private Integer detailID; // Khóa chính của bảng TransactionDetails

    @ManyToOne
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction; // Khóa ngoại tham chiếu đến bảng Transactions

    @ManyToOne
    @JoinColumn(name = "LotID", nullable = false)
    private Lot lot; // Khóa ngoại tham chiếu đến bảng Lots

    @Column(name = "Quantity", nullable = false)
    private Double quantity; // Số lượng của giao dịch
}


