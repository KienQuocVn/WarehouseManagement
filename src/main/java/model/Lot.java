package model;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lotID;

    @Column(name = "LotIDU", columnDefinition = "NVARCHAR(255)", unique = true, length = 20, nullable = false)
    private String lotIDU;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product; // Khóa ngoại đến bảng Products

    @Column(name = "ProductionTime", columnDefinition = "DATE", nullable = false)
    private LocalDateTime productionTime; // Thời gian sản xuất

    @Column(name = "ExpirationDays", columnDefinition = "DATE", nullable = false)
    private LocalDateTime expirationDays; // Hạn sử dụng

    @Column(name = "Weight", columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private BigDecimal weight; // Khối lượng tịnh

    @Column(name = "WarehouseWeight", columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private BigDecimal warehouseWeight; // Khối lượng cân

    @Column(name = "WeightDeviation", columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private BigDecimal weightDeviation; // Khối lượng bì
}


