package model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "Username", columnDefinition = "VARCHAR(255)", nullable = false)
    private String username; // Tên người dùng (Khóa chính)

    @Column(name = "FullName", columnDefinition = "VARCHAR(255)", nullable = false)
    private String fullName; // Tên đầy đủ của người dùng

    @Column(name = "Role", columnDefinition = "VARCHAR(255)", nullable = false)
    private String role; // Vai trò (Thủ kho, Quản lý, Nhân viên)
}
