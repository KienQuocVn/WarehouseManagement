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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productID;

    @Column(name = "ProductName", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String productName; // Tên sản phẩm

    @Column(name = "Color", columnDefinition = "NVARCHAR(255)")
    private String color; // Màu sắc sản phẩm
}
