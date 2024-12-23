package model;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionID;

    @Column(name = "TransactionType", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String transactionType; // Loại giao dịch ("Xuất" hoặc "Nhập")

    @Column(name = "Date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime date; // Ngày giao dịch

    @Column(name = "Customer", columnDefinition = "NVARCHAR(255)")
    private String customer; // Khách hàng (Chỉ áp dụng cho giao dịch "Xuất")

    @ManyToOne
    @JoinColumn(name = "Staff", nullable = false)
    private User staff; // Nhân viên thực hiện giao dịch
}

