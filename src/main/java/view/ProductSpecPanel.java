package view;

import javax.swing.*;
import java.awt.*;

public class ProductSpecPanel extends JPanel {
  public ProductSpecPanel() {
    setLayout(new BorderLayout());

    // Tiêu đề
    JLabel titleLabel = new JLabel("Quản Lý Quy Cách", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Bảng dữ liệu
    String[] columnNames = {"Mã Quy Cách", "Tên Quy Cách", "Mô Tả"};
    Object[][] data = {
        {"QC001", "Quy cách 1", "Mô tả quy cách 1"},
        {"QC002", "Quy cách 2", "Mô tả quy cách 2"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
    buttonPanel.add(new JButton("Thêm"));
    buttonPanel.add(new JButton("Sửa"));
    buttonPanel.add(new JButton("Xóa"));
    buttonPanel.add(new JButton("Nhập từ Excel"));
    buttonPanel.add(new JButton("Xuất ra Excel"));
    add(buttonPanel, BorderLayout.SOUTH);
  }
}
