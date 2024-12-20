package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Pallets")
public class Pallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PalletID")
    private Integer palletID; // Khóa chính của bảng Pallets

    @ManyToOne
    @JoinColumn(name = "LotID", nullable = false)
    private Lot lot; // Khóa ngoại tham chiếu đến bảng Lots
}
