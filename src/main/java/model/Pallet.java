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
@Table(name = "Pallets")
public class Pallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID palletID;

    @ManyToOne
    @JoinColumn(name = "LotID", nullable = false)
    private Lot lot; // Khóa ngoại tham chiếu đến bảng Lots
}

