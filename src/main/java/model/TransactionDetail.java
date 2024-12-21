package model;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TransactionDetails")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID detailID;

    @ManyToOne
    @JoinColumn(name = "TransactionID", nullable = false)
    private Transaction transaction; // Khóa ngoại tham chiếu đến bảng Transactions

    @ManyToOne
    @JoinColumn(name = "LotID", nullable = false)
    private Lot lot; // Khóa ngoại tham chiếu đến bảng Lots

    @Column(name = "Quantity", columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private Double quantity; // Số lượng của giao dịch
}



