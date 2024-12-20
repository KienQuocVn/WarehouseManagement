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
@Table(name = "WarehouseStaff")
public class WarehouseStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID staffId;

    @Column(name = "StaffName", columnDefinition = "VARCHAR(255)")
    private String staffName;
}

