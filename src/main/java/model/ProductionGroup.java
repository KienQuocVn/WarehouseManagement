package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "ProductionGroups")
public class ProductionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupID")
    private Integer groupID; // Khóa chính của bảng ProductionGroups

    @Column(name = "GroupName", nullable = false)
    private String groupName; // Tên nhóm sản xuất (ví dụ: "Tổ 1", "Tổ 2", ...)
}
