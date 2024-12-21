package model;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LotID")
    private Integer lotID;

    @Column(name = "LotIDU", unique = true, length = 20, nullable = false)
    private String lotIDU; // Đảm bảo LotIDU là duy nhất

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product; // Khóa ngoại đến bảng Products

    @Column(name = "ProductionTime", nullable = false)
    private LocalDateTime productionTime; // Thời gian sản xuất

    @Column(name = "ExpirationDays", nullable = false)
    private Integer expirationDays; // Hạn sử dụng

    @Column(name = "Weight", nullable = false)
    private BigDecimal weight; // Khối lượng tịnh

    @Column(name = "WarehouseWeight", nullable = false)
    private BigDecimal warehouseWeight; // Khối lượng cân

    @Column(name = "WeightDeviation", nullable = false)
    private BigDecimal weightDeviation; // Khối lượng bì
}

