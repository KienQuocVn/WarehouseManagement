package view;

import Utils.RoundedBorder;
import controller.CaiDonController;
import dao.DaoProduct;
import model.Product;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class CaiDonPanel extends JPanel {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private DaoProduct daoProduct;

  public CaiDonPanel() {
    setLayout(new BorderLayout());

    // Tạo container phía trên
    add(createSoftwarePanel(), BorderLayout.NORTH);


    // Thanh dashboard bên trái
    JPanel dashboard = new JPanel();
    dashboard.setLayout(new GridLayout(5, 1, 0, 0));
    dashboard.setPreferredSize(new Dimension(170, 0));

    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon = new javax.swing.ImageIcon(getClass().getResource("/img/document2.png"));
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
  // bien public
  JTable table;
  JTextField DHField;
  JTextField HSDField;
  JComboBox<String> ThukhoComboBox;
  private DefaultTableModel tableModel;
  private JPanel createMaHangPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    ActionListener ac = new CaiDonController(this);
    table = new JTable(); // Khởi tạo JTable tại đây
    // Bảng dữ liệu
    updateTableData();
    styleTable(table);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 3, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Tên Đơn Hàng");
     DHField = new JTextField("");
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
     HSDField = new JTextField("");
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
     ThukhoComboBox = new JComboBox<>(new String[]{"Xanh", "Đỏ","Vàng"});
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
    btnThem.addActionListener(ac);

    JButton btnSua = new JButton("Sửa");
    btnSua.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSua.setOpaque(true);
    btnSua.setBorderPainted(false);
    btnSua.addActionListener(ac);

    JButton btnXoa = new JButton("Xóa");
    btnXoa.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoa.setOpaque(true);
    btnXoa.setBorderPainted(false);
    btnXoa.addActionListener(ac);

    JButton btnXoaTatCa = new JButton("Xóa Tất Cả");
    btnXoaTatCa.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCa.setOpaque(true);
    btnXoaTatCa.setBorderPainted(false);
    btnXoaTatCa.addActionListener(ac);

    JButton btnReset = new JButton("Làm Mới");
    btnReset.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnReset.setOpaque(true);
    btnReset.setBorderPainted(false);
    btnReset.addActionListener(ac);
    // Thêm các nút vào panel
    buttonPanel.add(btnThem);
    buttonPanel.add(btnSua);
    buttonPanel.add(btnXoa);
    buttonPanel.add(btnXoaTatCa);
    buttonPanel.add(btnReset);

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
    // Panel chính
    JPanel containerPanel = new JPanel(new BorderLayout());

    // Panel chứa các thành phần form
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Khoảng cách xung quanh

    // Cài đặt chiều rộng cố định cho panel để nó sát bên trái
    panel.setPreferredSize(new Dimension(800, 0));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 20, 10, 10); // Điều chỉnh khoảng cách chiều ngang (left và right)

    // Thêm các thành phần vào form
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("THƯ MỤC LƯU:"), gbc);

    gbc.gridx = 1;
    gbc.weightx = 1.0; // Cho phép JTextField chiếm toàn bộ chiều rộng
    JTextField thuMucField = new JTextField("D:\\Export", 10);
    panel.add(thuMucField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("TÊN FILE:"), gbc);

    gbc.gridx = 1;
    JTextField tenFileField = new JTextField("Export.xlsx", 10);
    panel.add(tenFileField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("NGƯỠNG CẢN:"), gbc);

    gbc.gridx = 1;
    JTextField nguongCanField = new JTextField("10.00", 10);
    panel.add(nguongCanField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("MÁY IN:"), gbc);

    gbc.gridx = 1;
    JComboBox<String> mayInComboBox = new JComboBox<>(new String[] {
            "Foxit PhantomPDF Printer", "Microsoft Print to PDF", "Canon LBP6030"
    });
    panel.add(mayInComboBox, gbc);

    // Tạo panel trên form và thêm label
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.yellow)); // Border dưới
    JLabel headerLabel = new JLabel("Cấu Hình Hệ Thống");
    headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
    headerLabel.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái
    headerPanel.add(headerLabel, BorderLayout.WEST);

    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
    buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
    JButton btnThem = new JButton("Lưu");
    btnThem.setBackground(new Color(152, 201, 226, 255)); // Màu xanh lá
    btnThem.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThem.setBorderPainted(false);  // Bỏ viền của nút

    // Thêm các nút vào panel
    buttonPanel.add(btnThem);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Đặt panel vào vị trí sát bên trái
    containerPanel.add(headerPanel, BorderLayout.NORTH); // Đặt panel header lên trên
    containerPanel.add(panel, BorderLayout.CENTER); // Đặt panel form vào giữa
    containerPanel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return containerPanel;
  }


  private JPanel createSoftwarePanel2() {
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


  public void updateTableData() {
    DaoProduct daoProduct = new DaoProduct(); // Tạo đối tượng DAO
    List<Product> products = daoProduct.selectAll(); // Lấy danh sách sản phẩm từ DB

    // Chuyển đổi danh sách thành mảng hai chiều
    Object[][] data = new Object[products.size()][4];
    // Lưu lại giá trị ban đầu để so sánh
    Object[][] originalData = new Object[products.size()][4];
    for (int i = 0; i < products.size(); i++) {
      Product product = products.get(i);
      data[i][0] = i + 1; // STT (không cho phép sửa)
      data[i][1] = product.getProductName(); // Tên đơn hàng
      data[i][2] = product.getHSD(); // Hạn sử dụng
      data[i][3] = product.getColor(); // Màu (không cho phép sửa)

      // Lưu lại giá trị ban đầu
      originalData[i][0] = data[i][0];
      originalData[i][1] = data[i][1];
      originalData[i][2] = data[i][2];
      originalData[i][3] = data[i][3];
    }

    // Cập nhật model cho JTable
    tableModel = new DefaultTableModel(data, new Object[]{"STT", "Tên Đơn Hàng", "Hạn Sử Dụng", "Màu"}) {
      // Ghi đè phương thức isCellEditable để không cho phép chỉnh sửa cột 0 và cột 3
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0 && column != 3; // Chỉ cho phép chỉnh sửa cột khác cột 0 và cột 3
      }
    };
    table.setModel(tableModel);
    styleTable(table);

    // Đăng ký TableModelListener để nhận sự kiện thay đổi dữ liệu
    tableModel.addTableModelListener(new TableModelListener() {

      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();

          // Chỉ xử lý sự kiện khi sửa cột 1 (Tên Đơn Hàng) và cột 2 (Hạn Sử Dụng)
          if (column == 1 || column == 2) {
            String updatedValue = tableModel.getValueAt(row, column).toString();
            String originalValue = originalData[row][column].toString();

            // Kiểm tra xem giá trị nhập vào có khác với giá trị ban đầu không
            if (!updatedValue.equals(originalValue)) {
              System.out.println("Dữ liệu đã thay đổi tại dòng " + row + ", cột " + column + ": " + updatedValue);


            }
          }
        }
      }
    });
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


  //Getter va Setter
  public JTextField getDHField() {
    return DHField;
  }

  public JTextField getHSDField() {
    return HSDField;
  }

  public JComboBox<String> getThukhoComboBox() {
    return ThukhoComboBox;
  }



}
