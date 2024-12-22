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
@Table(name = "Shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID shiftId;

    @Column(name = "ShiftName", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String shiftName;
}


