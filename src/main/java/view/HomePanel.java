package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomePanel extends JPanel {
  public HomePanel() {
    setLayout(new BorderLayout());

    // Phần hiển thị các thẻ thống kê ở trên
    JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
    statsPanel.setBackground(Color.WHITE);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    statsPanel.add(createStatCard("27", "Sản phẩm trong kho", new Color(255, 193, 7), "monitor.png"));
    statsPanel.add(createStatCard("8", "Nhà cung cấp", new Color(255, 87, 34), "supplier.png"));
    statsPanel.add(createStatCard("4", "Tài khoản người dùng", new Color(33, 150, 243), "user.png"));

    add(statsPanel, BorderLayout.NORTH);

    // Phần hiển thị bảng dữ liệu
    String[] columnNames = {"STT", "Mã máy", "Tên máy", "Số lượng nhập", "Số lượng xuất"};
    Object[][] data = {
        {"1", "LP10", "Laptop Lenovo IdeaPad Gaming 3", "2", "1"},
        {"2", "LP14", "Laptop Lenovo IdeaPad 5 Pro", "4", "4"},
        {"3", "LP16", "Laptop Acer Nitro Gaming", "48", "6"},
        {"4", "LP17", "Laptop MSI Katana GF66", "3", "3"},
    };

    JTable table = new JTable(new DefaultTableModel(data, columnNames));
    JScrollPane scrollPane = new JScrollPane(table);

    add(scrollPane, BorderLayout.CENTER);
  }

  private JPanel createStatCard(String number, String text, Color color, String iconPath) {
    JPanel card = new JPanel();
    card.setLayout(new BorderLayout());
    card.setBackground(color);
    card.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    card.setPreferredSize(new Dimension(100, 100));

    JLabel numberLabel = new JLabel(number, JLabel.CENTER);
    JLabel textLabel = new JLabel(text, JLabel.CENTER);

    numberLabel.setFont(new Font("Arial", Font.BOLD, 36));
    textLabel.setFont(new Font("Arial", Font.PLAIN, 14));

    card.add(numberLabel, BorderLayout.CENTER);
    card.add(textLabel, BorderLayout.SOUTH);

    return card;
  }
}
