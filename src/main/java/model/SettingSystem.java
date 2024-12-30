package model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SettingSystem")
public class SettingSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    //thu muc luu
    @Column(name = "DirSave", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String DirSave;

    //ten file
    @Column(name = "NameFile", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String NameFile;

    @Column(name = "WeightThreshold", columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private BigDecimal WeightThreshold; // ngưỡng cân

    //May in
    @Column(name = "Printer", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String Printer;

}
