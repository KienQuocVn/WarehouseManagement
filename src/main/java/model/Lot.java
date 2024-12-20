package model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Lots")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lotId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String productionTime;

    @Column(nullable = false)
    private int expirationDays;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double warehouseWeight;

    @Column(nullable = false)
    private double weightDeviation;

    @ManyToOne
    @JoinColumn(name = "shiftId", nullable = false)
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false)
    private ProductionGroup group;

    @ManyToOne
    @JoinColumn(name = "warehouseStaff", nullable = false)
    private WarehouseStaff warehouseStaff;
}

