package view;

import javax.swing.*;
import java.awt.*;

public class InputWeightPanel extends JPanel {
  public InputWeightPanel() {
    setLayout(new BorderLayout());

    JLabel label = new JLabel("Nhập Mẻ Cân", JLabel.CENTER);
    label.setFont(new Font("Arial", Font.BOLD, 24));
    add(label, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
    formPanel.add(new JLabel("Mã Đơn Hàng:"));
    formPanel.add(new JTextField());
    formPanel.add(new JLabel("Quy Cách:"));
    formPanel.add(new JTextField());
    formPanel.add(new JLabel("Trọng Lượng:"));
    formPanel.add(new JTextField());
    formPanel.add(new JButton("Lưu"));
    formPanel.add(new JButton("In Phiếu Cân"));

    add(formPanel, BorderLayout.CENTER);
  }
}
