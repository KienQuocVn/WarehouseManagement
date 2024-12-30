package model;
import jakarta.persistence.*;
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
    @Column(name = "palletID", columnDefinition = "NVARCHAR(255)", length = 20)
    private String palletID;

    @ManyToOne
    @JoinColumn(name = "LotID", nullable = false)
    private Lot lot; // Khóa ngoại tham chiếu đến bảng Lots
}

