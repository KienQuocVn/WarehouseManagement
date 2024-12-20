package model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "Username")
    private String username; // Tên người dùng (Khóa chính)

    @Column(name = "FullName", nullable = false)
    private String fullName; // Tên đầy đủ của người dùng

    @Column(name = "Role", nullable = false)
    private String role; // Vai trò (Thủ kho, Quản lý, Nhân viên)
}