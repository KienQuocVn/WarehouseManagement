package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShiftID")
    private int shiftId;

    @Column(name = "ShiftName", nullable = false)
    private String shiftName;
}

