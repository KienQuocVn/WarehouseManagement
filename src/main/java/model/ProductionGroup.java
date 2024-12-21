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
@Table(name = "ProductionGroups")
public class ProductionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID groupID;

    @Column(name = "GroupName", columnDefinition = "VARCHAR(255)", nullable = false)
    private String groupName; // Tên nhóm sản xuất
}

