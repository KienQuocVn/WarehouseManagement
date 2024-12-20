package model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer productID; // Khóa chính của bảng Products

    @Column(name = "ProductName", nullable = false)
    private String productName; // Tên sản phẩm

    @Column(name = "Color")
    private String color; // Màu sắc sản phẩm
}