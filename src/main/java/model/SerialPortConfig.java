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
@Table(name = "SerialPortConfig")
public class SerialPortConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "comPort", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String comPort;        // Cổng COM

    @Column(name = "baudRate", columnDefinition = "INT", nullable = false)
    private int baudRate;          // Baud rate
    @Column(name = "dataBits", columnDefinition = "INT", nullable = false)
    private int dataBits;          // Data bits
    @Column(name = "stopBits", columnDefinition = "INT", nullable = false)
    private int stopBits;          // Stop bits
    @Column(name = "parityBits", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String parityBits;     // Parity bits


}
