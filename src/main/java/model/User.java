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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id; // Tên người dùng (Khóa chính)

    @Column(name = "FullName", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String fullName; // Tên đầy đủ của người dùng

}


