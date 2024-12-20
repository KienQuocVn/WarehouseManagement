package view;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {
  public HistoryPanel() {
    setLayout(new BorderLayout());

    // Tiêu đề
    JLabel titleLabel = new JLabel("Lịch Sử Cân", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Panel lọc dữ liệu
    JPanel filterPanel = new JPanel(new GridLayout(1, 6, 5, 5));
    filterPanel.add(new JLabel("Từ ngày:"));
    filterPanel.add(new JTextField());
    filterPanel.add(new JLabel("Đến ngày:"));
    filterPanel.add(new JTextField());
    filterPanel.add(new JLabel("Mã đơn hàng:"));
    filterPanel.add(new JTextField());
    JButton btnFilter = new JButton("Lọc");
    filterPanel.add(btnFilter);
    add(filterPanel, BorderLayout.NORTH);

    // Bảng dữ liệu
    String[] columnNames = {"Mã Cân", "Đơn Hàng", "Quy Cách", "Trọng Lượng", "Ngày Cân"};
    Object[][] data = {
        {"C001", "DH001", "QC001", "500 kg", "2024-06-01"},
        {"C002", "DH002", "QC002", "700 kg", "2024-06-02"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    // Nút xuất báo cáo
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(new JButton("Xuất Báo Cáo"));
    add(buttonPanel, BorderLayout.SOUTH);
  }
}
