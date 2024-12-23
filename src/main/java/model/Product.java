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
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productID;

    @Column(name = "ProductName", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String productName; // Tên sản phẩm

    @Column(name = "HSD", columnDefinition = "DECIMAL(10, 2)")
    private Double HSD; // Màu sắc sản phẩm

    @Column(name = "Color", columnDefinition = "NVARCHAR(255)")
    private String color; // Màu sắc sản phẩm
}
