package view;

import Utils.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaiDonPanel extends JPanel {
  private CardLayout cardLayout;
  private JPanel mainPanel;

  public CaiDonPanel() {
    setLayout(new BorderLayout());

    // Tạo container phía trên
    add(createSoftwarePanel(), BorderLayout.NORTH);


    // Thanh dashboard bên trái
    JPanel dashboard = new JPanel();
    dashboard.setLayout(new GridLayout(5, 1, 0, 0));
    dashboard.setPreferredSize(new Dimension(170, 0));

    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon = new javax.swing.ImageIcon(getClass().getResource("/img/document.png"));
    Image iconImage = originalIcon.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage = iconImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

    JButton btnMaHang = new JButton("Mã Hàng");
    btnMaHang.setBackground(Color.WHITE);
    btnMaHang.setIcon(new ImageIcon(scaledImage));
    btnMaHang.setFont(new Font("Arial", Font.BOLD, 14));
    //-------------- btn Ca san xuat
    JButton btnCaSanXuat = new JButton("Ca Sản Xuất");
    btnCaSanXuat.setBackground(Color.WHITE);
    btnCaSanXuat.setIcon(new ImageIcon(scaledImage));
    btnCaSanXuat.setFont(new Font("Arial", Font.BOLD, 14));
    //-------------- btn Tổ san xuat
    JButton btnToSanXuat = new JButton("Tổ Sản Xuất");
    btnToSanXuat.setBackground(Color.WHITE);
    btnToSanXuat.setIcon(new ImageIcon(scaledImage));
    btnToSanXuat.setFont(new Font("Arial", Font.BOLD, 14));
    //-------------- btn Thủ Kho
    JButton btnThuKho = new JButton("Thủ Kho");
    btnThuKho.setBackground(Color.WHITE);
    btnThuKho.setIcon(new ImageIcon(scaledImage));
    btnThuKho.setFont(new Font("Arial", Font.BOLD, 14));
    //-------------- btn Cấu Hình
    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon2 = new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"));
    Image iconImage2 = originalIcon2.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage2 = iconImage2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);


    JButton btnCauHinh = new JButton("Cấu Hình");
    btnCauHinh.setBackground(Color.WHITE);
    btnCauHinh.setIcon(new ImageIcon(scaledImage2));
    btnCauHinh.setFont(new Font("Arial", Font.BOLD, 14));
// Thiết lập kích thước cho các nút
    Dimension buttonSize = new Dimension(150, 30);  // Chiều rộng 120 và chiều cao 30

    btnMaHang.setPreferredSize(buttonSize);
    btnCaSanXuat.setPreferredSize(buttonSize);
    btnToSanXuat.setPreferredSize(buttonSize);
    btnThuKho.setPreferredSize(buttonSize);
    btnCauHinh.setPreferredSize(buttonSize);

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

  // Phương thức tạo panel tiêu đề phần mềm
  private JPanel createSoftwarePanel() {
    JPanel softwarePanel = new JPanel();
    softwarePanel.setBackground(Color.WHITE);
    softwarePanel.setBorder(new RoundedBorder(0));

    JLabel softwareLabel = new JLabel("CÀI ĐẶT ĐƠN HÀNG");
    softwareLabel.setVerticalAlignment(SwingConstants.TOP);
    softwareLabel.setFont(new Font("Arial", Font.BOLD, 16));
    softwarePanel.add(softwareLabel);

    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBackground(Color.WHITE);
    containerPanel.setPreferredSize(new Dimension(0, 40));
    containerPanel.add(softwarePanel, BorderLayout.CENTER);

    return containerPanel;
  }

  // MA HANG
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
    styleTable(table);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 3, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Tên Đơn Hàng");
    JTextField DHField = new JTextField("ĐƠN 1");
    DHField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    DHField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(DHField);

    // Panel2
    JPanel Panel2 = new JPanel();
    Panel2.setLayout(new BoxLayout(Panel2, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel2.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Thay đổi chiều rộng Panel2 bằng cách sử dụng setPreferredSize
    Panel2.setPreferredSize(new Dimension(200, 0));  // Chiều rộng 200px, chiều cao tự động

    JLabel Label2 = new JLabel("Hạn Sử Dụng (Ngày)");
    JTextField HSDField = new JTextField("4.0");
    HSDField.setFont(new Font("Arial", Font.BOLD, 14));

    Label2.setAlignmentX(Component.LEFT_ALIGNMENT);
    HSDField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel2.add(Label2);
    Panel2.add(HSDField);

    // Panel3
    JPanel Panel3 = new JPanel();
    Panel3.setLayout(new BoxLayout(Panel3, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel3.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Thay đổi chiều rộng Panel2 bằng cách sử dụng setPreferredSize
    Panel3.setPreferredSize(new Dimension(200, 0));  // Chiều rộng 200px, chiều cao tự động

    JLabel Label3 = new JLabel("Màu");
    Label3.setFont(new Font("Arial", Font.BOLD, 14));
    JComboBox<String> ThukhoComboBox = new JComboBox<>(new String[]{"Xanh", "Đỏ","Vàng"});
    ThukhoComboBox.setPreferredSize(new Dimension(200, 2));  // Chiều rộng 200px, chiều cao tự động

    Label3.setAlignmentX(Component.LEFT_ALIGNMENT);
    ThukhoComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel3.add(Label3);
    Panel3.add(ThukhoComboBox);

    PanelD.add(Panel1);
    PanelD.add(Panel2);
    PanelD.add(Panel3);

    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
    JButton btnThem = new JButton("Thêm");
    btnThem.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThem.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThem.setBorderPainted(false);  // Bỏ viền của nút

    JButton btnSua = new JButton("Sửa");
    btnSua.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSua.setOpaque(true);
    btnSua.setBorderPainted(false);

    JButton btnXoa = new JButton("Xóa");
    btnXoa.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoa.setOpaque(true);
    btnXoa.setBorderPainted(false);

    JButton btnXoaTatCa = new JButton("Xóa Tất Cả");
    btnXoaTatCa.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCa.setOpaque(true);
    btnXoaTatCa.setBorderPainted(false);

    // Thêm các nút vào panel
    buttonPanel.add(btnThem);
    buttonPanel.add(btnSua);
    buttonPanel.add(btnXoa);
    buttonPanel.add(btnXoaTatCa);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }




  private JPanel createCaSanXuatPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Ca Sản Xuất"};
    Object[][] data = {
            {"1", "Ca 1"},
            {"2", "Ca 2"},
            {"3", "Ca 3"}
    };
    JTable table = new JTable(data, columnNames);
    styleTable2(table);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 1, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Ca Sản Xuất");
    JTextField DHField = new JTextField("Ca 4");
    DHField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    DHField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(DHField);

    PanelD.add(Panel1);


    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
    JButton btnThem = new JButton("Thêm");
    btnThem.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThem.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThem.setBorderPainted(false);  // Bỏ viền của nút

    JButton btnSua = new JButton("Sửa");
    btnSua.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSua.setOpaque(true);
    btnSua.setBorderPainted(false);

    JButton btnXoa = new JButton("Xóa");
    btnXoa.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoa.setOpaque(true);
    btnXoa.setBorderPainted(false);

    JButton btnXoaTatCa = new JButton("Xóa Tất Cả");
    btnXoaTatCa.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCa.setOpaque(true);
    btnXoaTatCa.setBorderPainted(false);

    // Thêm các nút vào panel
    buttonPanel.add(btnThem);
    buttonPanel.add(btnSua);
    buttonPanel.add(btnXoa);
    buttonPanel.add(btnXoaTatCa);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }

  private JPanel createToSanXuatPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Tổ Sản Xuất"};
    Object[][] data = {
            {"1", "Tổ 1"},
            {"2", "Tổ 2"},
            {"3", "Tổ 3"}
    };
    JTable table = new JTable(data, columnNames);
    styleTable2(table);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 1, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Tổ Sản Xuất");
    JTextField DHField = new JTextField("Tổ 4");
    DHField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    DHField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(DHField);

    PanelD.add(Panel1);


    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
    JButton btnThem = new JButton("Thêm");
    btnThem.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThem.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThem.setBorderPainted(false);  // Bỏ viền của nút

    JButton btnSua = new JButton("Sửa");
    btnSua.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSua.setOpaque(true);
    btnSua.setBorderPainted(false);

    JButton btnXoa = new JButton("Xóa");
    btnXoa.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoa.setOpaque(true);
    btnXoa.setBorderPainted(false);

    JButton btnXoaTatCa = new JButton("Xóa Tất Cả");
    btnXoaTatCa.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCa.setOpaque(true);
    btnXoaTatCa.setBorderPainted(false);

    // Thêm các nút vào panel
    buttonPanel.add(btnThem);
    buttonPanel.add(btnSua);
    buttonPanel.add(btnXoa);
    buttonPanel.add(btnXoaTatCa);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }

  private JPanel createThuKhoPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Bảng dữ liệu
    String[] columnNames = {"STT", "Thủ Kho"};
    Object[][] data = {
            {"1", "Thủ Kho 1"},
            {"2", "Thủ Kho 2 "},
            {"3", "Thủ Kho 3"}
    };
    JTable table = new JTable(data, columnNames);
    styleTable2(table);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 1, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Thủ Kho");
    JTextField DHField = new JTextField("Thủ Kho 4");
    DHField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    DHField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(DHField);

    PanelD.add(Panel1);


    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
    JButton btnThem = new JButton("Thêm");
    btnThem.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThem.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThem.setBorderPainted(false);  // Bỏ viền của nút

    JButton btnSua = new JButton("Sửa");
    btnSua.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSua.setOpaque(true);
    btnSua.setBorderPainted(false);

    JButton btnXoa = new JButton("Xóa");
    btnXoa.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoa.setOpaque(true);
    btnXoa.setBorderPainted(false);

    JButton btnXoaTatCa = new JButton("Xóa Tất Cả");
    btnXoaTatCa.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCa.setOpaque(true);
    btnXoaTatCa.setBorderPainted(false);

    // Thêm các nút vào panel
    buttonPanel.add(btnThem);
    buttonPanel.add(btnSua);
    buttonPanel.add(btnXoa);
    buttonPanel.add(btnXoaTatCa);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }

  private JPanel createCauHinhPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Nội dung tab Cấu Hình", JLabel.CENTER), BorderLayout.CENTER);

    return panel;
  }

  // Phương thức tùy chỉnh bảng JTable
  private void styleTable(JTable table) {
    table.setRowHeight(30);
    table.setFont(new Font("Arial", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    table.getTableHeader().setBackground(new Color(255, 153, 51));
    table.getTableHeader().setForeground(Color.BLACK);
    table.setGridColor(Color.GRAY);
    table.setSelectionBackground(new Color(204, 229, 255));
    table.setSelectionForeground(Color.BLACK);

    TableColumnModel columnModel = table.getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(1);
    columnModel.getColumn(1).setPreferredWidth(650);
    columnModel.getColumn(2).setPreferredWidth(250);
    columnModel.getColumn(3).setPreferredWidth(150);


    // Màu nền xen kẽ
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!isSelected) {
          component.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
        }
        return component;
      }
    });

    // Căn lề giữa cho các cột
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
  }

  private void styleTable2(JTable table) {
    table.setRowHeight(30);
    table.setFont(new Font("Arial", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    table.getTableHeader().setBackground(new Color(255, 153, 51));
    table.getTableHeader().setForeground(Color.BLACK);
    table.setGridColor(Color.GRAY);
    table.setSelectionBackground(new Color(204, 229, 255));
    table.setSelectionForeground(Color.BLACK);

    TableColumnModel columnModel = table.getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(50);  // Cột STT
    columnModel.getColumn(1).setPreferredWidth(1050);  // Cột Tên Đơn Hàng

    // Màu nền xen kẽ
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!isSelected) {
          component.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
        }
        return component;
      }
    });

    // Căn lề giữa cho các cột
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < table.getColumnCount(); i++) {
      // Căn lề giữa cho tất cả các cột
      table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Căn lề trái cho cột "STT" (cột 0) và "Tên Đơn Hàng" (cột 1)
    DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
    leftRenderer.setHorizontalAlignment(JLabel.LEFT);
    table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);  // Cột STT
    table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);  // Cột Tên Đơn Hàng
  }

}
