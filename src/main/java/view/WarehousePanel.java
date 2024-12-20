package view;

import javax.swing.*;
import java.awt.*;

public class WarehousePanel extends JPanel {
  public WarehousePanel() {
    setLayout(new BorderLayout());

    // Tiêu đề
    JLabel titleLabel = new JLabel("Quản Lý Kho", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Bảng dữ liệu
    String[] columnNames = {"Mã Đơn Hàng", "Tên Hàng", "Số Lượng", "Trạng Thái"};
    Object[][] data = {
        {"DH001", "Hàng 1", "500 kg", "Còn trong kho"},
        {"DH002", "Hàng 2", "300 kg", "Xuất kho"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
    buttonPanel.add(new JButton("Thêm Hàng"));
    buttonPanel.add(new JButton("Xuất Kho"));
    buttonPanel.add(new JButton("Xóa Hàng"));
    add(buttonPanel, BorderLayout.SOUTH);
  }
}
