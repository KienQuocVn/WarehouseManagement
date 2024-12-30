package model;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//thu kho
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WarehouseStaff")
public class WarehouseStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffId;

    @Column(name = "StaffName", columnDefinition = "NVARCHAR(255)", nullable = false, unique = true)
    private String staffName;
}

