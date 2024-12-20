package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ThongKePanel extends JPanel {

  public ThongKePanel() {
    setLayout(new BorderLayout());

    // Header panel
    JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Tiêu đề
    JLabel titleLabel = new JLabel("KẾT QUẢ CẦN BÁN THÀNH PHẨM", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

    // Filter section
    JPanel filterPanel = new JPanel(new GridLayout(2, 6, 10, 10));

    JLabel lblToSanXuat = new JLabel("Tổ Sản Xuất:");
    JComboBox<String> cbToSanXuat = new JComboBox<>(new String[]{"Tất cả", "Tổ 1", "Tổ 2"});
    JLabel lblCaSanXuat = new JLabel("Ca Sản Xuất:");
    JComboBox<String> cbCaSanXuat = new JComboBox<>(new String[]{"Tất cả", "Ca 1", "Ca 2"});
    JLabel lblMaHang = new JLabel("Mã Hàng:");
    JComboBox<String> cbMaHang = new JComboBox<>(new String[]{"Tất cả", "MH01", "MH02"});

    JLabel lblTuNgay = new JLabel("Từ Ngày:");
    JTextField tfTuNgay = new JTextField(10);
    JLabel lblDenNgay = new JLabel("Đến Ngày:");
    JTextField tfDenNgay = new JTextField(10);
    JButton btnTimKiem = new JButton("Tìm Kiếm");
    JButton btnXoaChon = new JButton("Xóa Chọn");

    filterPanel.add(lblToSanXuat);
    filterPanel.add(cbToSanXuat);
    filterPanel.add(lblCaSanXuat);
    filterPanel.add(cbCaSanXuat);
    filterPanel.add(lblMaHang);
    filterPanel.add(cbMaHang);

    filterPanel.add(lblTuNgay);
    filterPanel.add(tfTuNgay);
    filterPanel.add(lblDenNgay);
    filterPanel.add(tfDenNgay);
    filterPanel.add(btnTimKiem);
    filterPanel.add(btnXoaChon);

    headerPanel.add(titleLabel, BorderLayout.NORTH);
    headerPanel.add(filterPanel, BorderLayout.CENTER);
    add(headerPanel, BorderLayout.NORTH);
    add(headerPanel, BorderLayout.NORTH);

    // Table section
    String[] columnNames = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân", "KL Bì", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet", "Xuất/Nhập"};
    Object[][] data = {
        {"6", "Đơn 2", "0408N", "Tổ 1", "Ca 1", "12:00", "12.0", "0.0", "12.0", "HỦY", "26/05/2024", "4.0", "HỦY"},
        {"6", "Đơn 2", "0408N", "Tổ 1", "Ca 1", "12:00", "12.0", "0.0", "12.0", "HỦY", "26/05/2024", "4.0", "HỦY"}
    };

    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Customizing table
    table.setRowHeight(25);
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
    table.setFont(new Font("Arial", Font.PLAIN, 12));

    add(scrollPane, BorderLayout.CENTER);

    // Footer section
    JPanel footerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
    footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel summaryPanel = new JPanel(new GridLayout(1, 8, 10, 10));
    summaryPanel.add(new JLabel("Số Phiếu:"));
    summaryPanel.add(new JTextField(10));
    summaryPanel.add(new JLabel("Khối Lượng Tổng:"));
    summaryPanel.add(new JTextField(10));
    summaryPanel.add(new JLabel("Ngày Sản Xuất:"));
    summaryPanel.add(new JTextField(10));
    summaryPanel.add(new JLabel("Thủ Kho:"));
    summaryPanel.add(new JTextField(10));

    JPanel actionPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    JButton btnLuuThayDoi = new JButton("Lưu Thay Đổi");
    JButton btnXoaDuLieu = new JButton("Xóa Dữ Liệu");
    JButton btnInPhieu = new JButton("In Phiếu");
    JButton btnXoaTatCa = new JButton("Xóa Tất Cả");

    actionPanel.add(btnLuuThayDoi);
    actionPanel.add(btnXoaDuLieu);
    actionPanel.add(btnInPhieu);
    actionPanel.add(btnXoaTatCa);

    footerPanel.add(summaryPanel);
    footerPanel.add(actionPanel);

    add(footerPanel, BorderLayout.SOUTH);
  }


}
