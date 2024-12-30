package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Lots")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LotID")
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

    // Các mối quan hệ với bảng Shifts, ProductionGroups, WarehouseStaff
    @ManyToOne
    @JoinColumn(name = "ShiftID", nullable = false)
    private Shift shift; // Tham chiếu đến bảng Shifts

    @ManyToOne
    @JoinColumn(name = "GroupID", nullable = false)
    private ProductionGroup productionGroup; // Tham chiếu đến bảng ProductionGroups

    @ManyToOne
    @JoinColumn(name = "WarehouseStaffID", nullable = false)
    private WarehouseStaff warehouseStaff; // Tham chiếu đến bảng WarehouseStaff

    @ManyToOne
    @JoinColumn(name = "PalletID", nullable = false)
    private Pallet pallets;
}
