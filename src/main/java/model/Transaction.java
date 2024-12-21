package model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Integer transactionID; // Khóa chính của bảng Transactions

    @Column(name = "TransactionType", nullable = false)
    private String transactionType; // Loại giao dịch ("Xuất" hoặc "Nhập")

    @Column(name = "Date", nullable = false)
    private LocalDateTime date; // Ngày giao dịch

    @Column(name = "Customer")
    private String customer; // Khách hàng (Chỉ áp dụng cho giao dịch "Xuất")

    @ManyToOne
    @JoinColumn(name = "Staff", nullable = false)
    private User staff; // Nhân viên thực hiện giao dịch (khóa ngoại tham chiếu đến bảng Users)
}
