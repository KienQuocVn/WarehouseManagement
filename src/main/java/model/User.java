package model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String role;
}