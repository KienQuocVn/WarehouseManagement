package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
