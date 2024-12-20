package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "ProductionGroups")
public class ProductionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    @Column(nullable = false)
    private String groupName;
}
