package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shiftId;

    @Column(nullable = false)
    private String shiftName;
}

