package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaiDonPanel extends JPanel {
  private CardLayout cardLayout;
  private JPanel mainPanel;

  public CaiDonPanel() {
    setLayout(new BorderLayout());

    // Thanh dashboard bên trái
    JPanel dashboard = new JPanel();
    dashboard.setLayout(new GridLayout(5, 1, 5, 5));
    dashboard.setPreferredSize(new Dimension(150, 0));

    JButton btnMaHang = new JButton("Mã Hàng");
    JButton btnCaSanXuat = new JButton("Ca Sản Xuất");
    JButton btnToSanXuat = new JButton("Tổ Sản Xuất");
    JButton btnThuKho = new JButton("Thủ Kho");
    JButton btnCauHinh = new JButton("Cấu Hình");

    dashboard.add(btnMaHang);
    dashboard.add(btnCaSanXuat);
    dashboard.add(btnToSanXuat);
    dashboard.add(btnThuKho);
    dashboard.add(btnCauHinh);

    add(dashboard, BorderLayout.WEST);

    // Panel chính hiển thị nội dung theo tab
    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    // Thêm các tab vào panel chính
    mainPanel.add(createMaHangPanel(), "MaHang");
    mainPanel.add(createCaSanXuatPanel(), "CaSanXuat");
    mainPanel.add(createToSanXuatPanel(), "ToSanXuat");
    mainPanel.add(createThuKhoPanel(), "ThuKho");
    mainPanel.add(createCauHinhPanel(), "CauHinh");

    add(mainPanel, BorderLayout.CENTER);

    // Sự kiện chuyển đổi tab
    btnMaHang.addActionListener(e -> cardLayout.show(mainPanel, "MaHang"));
    btnCaSanXuat.addActionListener(e -> cardLayout.show(mainPanel, "CaSanXuat"));
    btnToSanXuat.addActionListener(e -> cardLayout.show(mainPanel, "ToSanXuat"));
    btnThuKho.addActionListener(e -> cardLayout.show(mainPanel, "ThuKho"));
    btnCauHinh.addActionListener(e -> cardLayout.show(mainPanel, "CauHinh"));
  }

  private JPanel createMaHangPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Tên Đơn Hàng", "Hạn Sử Dụng (Ngày)", "Màu"};
    Object[][] data = {
        {"1", "Đơn 1", "2.0", "Đỏ"},
        {"2", "Đơn 2", "4.0", "Vàng"},
        {"3", "Đơn 3", "4.0", "Xanh"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(new JButton("Thêm"));
    buttonPanel.add(new JButton("Sửa"));
    buttonPanel.add(new JButton("Xóa"));
    buttonPanel.add(new JButton("Xóa Tất Cả"));
    buttonPanel.add(new JButton("Trang Chủ"));

    panel.add(buttonPanel, BorderLayout.SOUTH);
    return panel;
  }

  private JPanel createCaSanXuatPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Ca Sản xuất"};
    Object[][] data = {
        {"1", "Đơn 1"},
        {"2", "Đơn 2"},
        {"3", "Đơn 3"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(new JButton("Thêm"));
    buttonPanel.add(new JButton("Sửa"));
    buttonPanel.add(new JButton("Xóa"));
    buttonPanel.add(new JButton("Xóa Tất Cả"));
    buttonPanel.add(new JButton("Trang Chủ"));

    panel.add(buttonPanel, BorderLayout.SOUTH);
    return panel;
  }

  private JPanel createToSanXuatPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Tổ Sản xuất"};
    Object[][] data = {
        {"1", "Đơn 1"},
        {"2", "Đơn 2"},
        {"3", "Đơn 3"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(new JButton("Thêm"));
    buttonPanel.add(new JButton("Sửa"));
    buttonPanel.add(new JButton("Xóa"));
    buttonPanel.add(new JButton("Xóa Tất Cả"));
    buttonPanel.add(new JButton("Trang Chủ"));

    panel.add(buttonPanel, BorderLayout.SOUTH);
    return panel;
  }

  private JPanel createThuKhoPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Thủ Kho"};
    Object[][] data = {
        {"1", "Đơn 1"},
        {"2", "Đơn 2"},
        {"3", "Đơn 3"}
    };
    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(new JButton("Thêm"));
    buttonPanel.add(new JButton("Sửa"));
    buttonPanel.add(new JButton("Xóa"));
    buttonPanel.add(new JButton("Xóa Tất Cả"));
    buttonPanel.add(new JButton("Trang Chủ"));

    panel.add(buttonPanel, BorderLayout.SOUTH);
    return panel;
  }

  private JPanel createCauHinhPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Nội dung tab Cấu Hình", JLabel.CENTER), BorderLayout.CENTER);
    return panel;
  }

}
