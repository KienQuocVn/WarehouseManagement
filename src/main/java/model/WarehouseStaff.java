package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "WarehouseStaff")
public class WarehouseStaff {
    @Id
    @Column(name = "StaffID")
    private int staffId;

    @Column(name = "StaffName")
    private String staffName;
}
