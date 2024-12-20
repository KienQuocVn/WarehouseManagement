package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Pallets")
public class Pallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int palletId;

    @ManyToOne
    @JoinColumn(name = "lotId", nullable = false)
    private Lot lot;
}
