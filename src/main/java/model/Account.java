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
@Table(name = "Accounts")
public class Account {

  @Id
  @Column(columnDefinition = "VARCHAR(255)", nullable = false)
  private String username;

  @Column(columnDefinition = "VARCHAR(255)")
  private String password;

  @Column(columnDefinition = "VARCHAR(255)")
  private String fullname;

  @Column(columnDefinition = "VARCHAR(255)", unique = true)
  private String email;

}
