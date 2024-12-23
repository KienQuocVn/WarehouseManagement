package view;

import javax.swing.*;
import java.awt.*;

public class SerialPortConfigFrame extends JFrame {
  public SerialPortConfigFrame() {
    setTitle("Cài đặt");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(420, 400);
    setLocationRelativeTo(null);
    ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource("img/logo.png"));
// Tăng kích thước của logo, ví dụ thay đổi kích thước thành 32x32
    Image img = logo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

// Gán lại hình ảnh đã thay đổi kích thước
    logo = new ImageIcon(img);

// Cài đặt icon cho cửa sổ
    setIconImage(logo.getImage());
    // Tạo panel chính
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Tiêu đề
    JLabel titleLabel = new JLabel("CẤU HÌNH SERIAL PORT", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    mainPanel.add(titleLabel, BorderLayout.NORTH);

    // Panel trung tâm chứa cấu hình
    JPanel configPanel = new JPanel();
    configPanel.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout
    configPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
    gbc.fill = GridBagConstraints.HORIZONTAL; // Đảm bảo các JComboBox full chiều rộng
    gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng cột

// COM PORT
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.1; // Tỷ lệ label
    configPanel.add(new JLabel("COM PORT:", JLabel.LEFT), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9; // Tỷ lệ JComboBox
    JComboBox<String> comPortBox = new JComboBox<>(new String[]{"COM1", "COM2", "COM3", "COM4"});
    configPanel.add(comPortBox, gbc);

// BAUD RATE
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.1;
    configPanel.add(new JLabel("BAUD RATE:", JLabel.LEFT), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JComboBox<String> baudRateBox = new JComboBox<>(new String[]{"9600", "19200", "38400", "57600", "115200"});
    configPanel.add(baudRateBox, gbc);

// DATA BITS
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0.1;
    configPanel.add(new JLabel("DATA BITS:", JLabel.LEFT), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JComboBox<String> dataBitsBox = new JComboBox<>(new String[]{"5", "6", "7", "8"});
    configPanel.add(dataBitsBox, gbc);

// STOP BITS
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.weightx = 0.1;
    configPanel.add(new JLabel("STOP BITS:", JLabel.LEFT), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JComboBox<String> stopBitsBox = new JComboBox<>(new String[]{"One", "Two"});
    configPanel.add(stopBitsBox, gbc);

// PARITY BITS
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.weightx = 0.1;
    configPanel.add(new JLabel("PARITY BITS:", JLabel.LEFT), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JComboBox<String> parityBitsBox = new JComboBox<>(new String[]{"None", "Odd", "Even", "Mark", "Space"});
    configPanel.add(parityBitsBox, gbc);


    // PARITY BITS
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.weightx = 0.1;
    configPanel.add(new JLabel("Trạng thái:", JLabel.LEFT), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JLabel statusLabel = new JLabel("Chưa kết nối");
    statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
    statusLabel.setForeground(Color.RED);
    configPanel.add(statusLabel, gbc);

    mainPanel.add(configPanel, BorderLayout.CENTER);


    // Panel chứa các nút
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));

    JButton connectButton = createButton("Kết Nối");
    connectButton.setPreferredSize(new Dimension(100, 35)); // 100px rộng, 50px cao

    JButton disconnectButton = createButton("Ngắt");
    disconnectButton.setPreferredSize(new Dimension(100, 35)); // 100px rộng, 50px cao

    JButton saveButton = createButton("Lưu");
    saveButton.setPreferredSize(new Dimension(100, 35)); // 100px rộng, 50px cao

    buttonPanel.add(connectButton);
    buttonPanel.add(disconnectButton);
    buttonPanel.add(saveButton);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Thêm panel chính vào frame
    add(mainPanel);

    setVisible(true);
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 15));
    button.setBackground(new Color(70, 130, 180));
    button.setForeground(Color.BLACK);
    button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)));
    button.setFocusPainted(false);
    return button;
  }
}
