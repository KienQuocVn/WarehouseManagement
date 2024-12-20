package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ThongKePanel extends JPanel {

  public ThongKePanel() {
    setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel filterPanel = new JPanel(new GridLayout(1, 6, 5, 5));
    filterPanel.add(new JLabel("Tổ Sản Xuất:"));
    filterPanel.add(new JComboBox<>(new String[]{"Tất cả", "Tổ 1", "Tổ 2"}));
    filterPanel.add(new JLabel("Ca Sản Xuất:"));
    filterPanel.add(new JComboBox<>(new String[]{"Tất cả", "Ca 1", "Ca 2"}));
    filterPanel.add(new JLabel("Mã Hàng:"));
    filterPanel.add(new JComboBox<>(new String[]{"Tất cả", "MH01", "MH02"}));

    JPanel datePanel = new JPanel(new GridLayout(1, 6, 5, 5));
    datePanel.add(new JLabel("Từ Ngày:"));
    datePanel.add(new JTextField(10));
    datePanel.add(new JLabel("Đến Ngày:"));
    datePanel.add(new JTextField(10));
    datePanel.add(new JButton("Tìm Kiếm"));
    datePanel.add(new JButton("Xóa Chọn"));

    headerPanel.add(filterPanel);
    headerPanel.add(datePanel);
    add(headerPanel, BorderLayout.NORTH);

    // Table section
    String[] columnNames = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân", "KL Bì", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet", "Xuất/Nhập"};
    Object[][] data = {
        {"6", "Đơn 2", "0408N", "Tổ 1", "Ca 1", "12:00", "12.0", "0.0", "12.0", "HỦY", "26/05/2024", "4.0", "HỦY"},
        {"6", "Đơn 2", "0408N", "Tổ 1", "Ca 1", "12:00", "12.0", "0.0", "12.0", "HỦY", "26/05/2024", "4.0", "HỦY"}
    };

    JTable table = new JTable(new DefaultTableModel(data, columnNames));
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    // Footer section
    JPanel footerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 5, 5));
    summaryPanel.add(new JLabel("Số Phiếu:"));
    summaryPanel.add(new JTextField(10));
    summaryPanel.add(new JLabel("Khối Lượng Tổng:"));
    summaryPanel.add(new JTextField(10));

    JPanel actionPanel = new JPanel(new GridLayout(1, 4, 5, 5));
    actionPanel.add(new JButton("Lưu Thay Đổi"));
    actionPanel.add(new JButton("Xóa Dữ Liệu"));
    actionPanel.add(new JButton("In Phiếu"));
    actionPanel.add(new JButton("Xóa Tất Cả"));

    footerPanel.add(summaryPanel);
    footerPanel.add(actionPanel);
    add(footerPanel, BorderLayout.SOUTH);
  }


}
