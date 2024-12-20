package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "WarehouseStaff")
public class WarehouseStaff {
    @Id
    private int staffId;

    @Column(nullable = false)
    private String staffName;
}
