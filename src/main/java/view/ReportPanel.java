package view;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
  public ReportPanel() {
    setLayout(new BorderLayout());

    // Tiêu đề
    JLabel titleLabel = new JLabel("Xuất Báo Cáo", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Panel chọn loại báo cáo
    JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    optionsPanel.add(new JLabel("Chọn Loại Báo Cáo:"));
    String[] reportTypes = {"Lịch sử cân", "Báo cáo tồn kho"};
    JComboBox<String> comboBox = new JComboBox<>(reportTypes);
    optionsPanel.add(comboBox);

    optionsPanel.add(new JLabel("Ngày Báo Cáo:"));
    optionsPanel.add(new JTextField());
    add(optionsPanel, BorderLayout.CENTER);

    // Nút chức năng
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(new JButton("Xuất Báo Cáo"));
    add(buttonPanel, BorderLayout.SOUTH);
  }
}
