package view;

import Utils.RoundedBorder;
import controller.CaiDonController;
import controller.ProductionGroupController;
import controller.ShiftController;
import controller.WarehouseStaffController;
import dao.DaoProduct;
import dao.DaoProductionGroup;
import dao.DaoShift;
import dao.DaoWarehouseStaff;
import model.Product;
import Utils.DialogHelper;
import model.ProductionGroup;
import model.Shift;
import model.WarehouseStaff;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
public class CaiDonPanel extends JPanel {
  private CardLayout cardLayout;
  private JPanel mainPanel;
  public Product productSendController;
  public Shift ShiftSendController;
  public ProductionGroup ProductionGroupSendController;
  public WarehouseStaff WarehouseStaffSendController;

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
  JTable tableMaHang;
  JTextField DHField;
  JTextField HSDField;
  JComboBox<String> ThukhoComboBox;
  private DefaultTableModel tableModel;
  JButton btnThemMaHang;
  JButton btnSuaMaHang;
  JButton btnXoaMaHang;
  JButton btnXoaTatCaMaHang;
  JButton btnResetMaHang;
  private JPanel createMaHangPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    ActionListener ac = new CaiDonController(this);
    tableMaHang = new JTable(); // Khởi tạo JTable tại đây
    // Bảng dữ liệu
    updateTableData();
    styleTable(tableMaHang);
    JScrollPane scrollPane = new JScrollPane(tableMaHang);
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
     btnThemMaHang = new JButton("Thêm");
    btnThemMaHang.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThemMaHang.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThemMaHang.setBorderPainted(false);  // Bỏ viền của nút
    btnThemMaHang.addActionListener(ac);

     btnSuaMaHang = new JButton("Sửa");
    btnSuaMaHang.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSuaMaHang.setOpaque(true);
    btnSuaMaHang.setBorderPainted(false);
    btnSuaMaHang.addActionListener(ac);

     btnXoaMaHang = new JButton("Xóa");
    btnXoaMaHang.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoaMaHang.setOpaque(true);
    btnXoaMaHang.setBorderPainted(false);
    btnXoaMaHang.addActionListener(ac);

     btnXoaTatCaMaHang = new JButton("Xóa Tất Cả");
    btnXoaTatCaMaHang.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCaMaHang.setOpaque(true);
    btnXoaTatCaMaHang.setBorderPainted(false);
    btnXoaTatCaMaHang.addActionListener(ac);

     btnResetMaHang = new JButton("Làm Mới");
    btnResetMaHang.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnResetMaHang.setOpaque(true);
    btnResetMaHang.setBorderPainted(false);
    btnResetMaHang.addActionListener(ac);
    // Thêm các nút vào panel
    buttonPanel.add(btnThemMaHang);
    buttonPanel.add(btnSuaMaHang);
    buttonPanel.add(btnXoaMaHang);
    buttonPanel.add(btnXoaTatCaMaHang);
    buttonPanel.add(btnResetMaHang);
    btnSuaMaHang.setEnabled(false);
    btnXoaMaHang.setEnabled(false);
    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }



  JTable tableShift;
  private DefaultTableModel tableModelShift;
  JTextField CSXShiftField;
  JButton btnThemShift;
  JButton btnSuaShift;
  JButton btnXoaShift;
  JButton btnXoaTatCaShift;
  JButton btnResetShift;
  private JPanel createCaSanXuatPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    ActionListener Sc = new ShiftController(this);

     tableShift = new JTable();
    updateTableDataShift();
    styleTable2(tableShift);
    JScrollPane scrollPane = new JScrollPane(tableShift);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 1, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Ca Sản Xuất");
    CSXShiftField = new JTextField();
    CSXShiftField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    CSXShiftField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(CSXShiftField);

    PanelD.add(Panel1);


    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
     btnThemShift = new JButton("Thêm");
    btnThemShift.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThemShift.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThemShift.setBorderPainted(false);  // Bỏ viền của nút
    btnThemShift.addActionListener(Sc);

    btnSuaShift = new JButton("Sửa");
    btnSuaShift.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSuaShift.setOpaque(true);
    btnSuaShift.setBorderPainted(false);
    btnSuaShift.addActionListener(Sc);

     btnXoaShift = new JButton("Xóa");
    btnXoaShift.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoaShift.setOpaque(true);
    btnXoaShift.setBorderPainted(false);
    btnXoaShift.addActionListener(Sc);

     btnXoaTatCaShift = new JButton("Xóa Tất Cả");
    btnXoaTatCaShift.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCaShift.setOpaque(true);
    btnXoaTatCaShift.setBorderPainted(false);
    btnXoaTatCaShift.addActionListener(Sc);

    btnResetShift = new JButton("Làm Mới");
    btnResetShift.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnResetShift.setOpaque(true);
    btnResetShift.setBorderPainted(false);
    btnResetShift.addActionListener(Sc);

    // Thêm các nút vào panel
    buttonPanel.add(btnThemShift);
    buttonPanel.add(btnSuaShift);
    buttonPanel.add(btnXoaShift);
    buttonPanel.add(btnXoaTatCaShift);
    buttonPanel.add(btnResetShift);
    btnSuaShift.setEnabled(false);
    btnXoaShift.setEnabled(false);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }

  JTable tableProductionGroup;
  private DefaultTableModel tableModelProductionGroup;
  JTextField TSXField;
  JButton btnThemProductionGroup;
  JButton btnSuaProductionGroup;
  JButton btnXoaProductionGroup;
  JButton btnXoaTatCaProductionGroup;
  JButton btnResetProductionGroup;
  private JPanel createToSanXuatPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    ActionListener PGc = new ProductionGroupController(this);

    tableProductionGroup  = new JTable();
    updateTableDataProductionGroup();
    styleTable2(tableProductionGroup);
    JScrollPane scrollPane = new JScrollPane(tableProductionGroup);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 1, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Tổ Sản Xuất");
    TSXField = new JTextField("");
    TSXField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    TSXField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(TSXField);

    PanelD.add(Panel1);


    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
    btnThemProductionGroup = new JButton("Thêm");
    btnThemProductionGroup.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThemProductionGroup.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThemProductionGroup.setBorderPainted(false);  // Bỏ viền của nút
    btnThemProductionGroup.addActionListener(PGc);

    btnSuaProductionGroup = new JButton("Sửa");
    btnSuaProductionGroup.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSuaProductionGroup.setOpaque(true);
    btnSuaProductionGroup.setBorderPainted(false);
    btnSuaProductionGroup.addActionListener(PGc);

    btnXoaProductionGroup = new JButton("Xóa");
    btnXoaProductionGroup.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoaProductionGroup.setOpaque(true);
    btnXoaProductionGroup.setBorderPainted(false);
    btnXoaProductionGroup.addActionListener(PGc);

    btnXoaTatCaProductionGroup = new JButton("Xóa Tất Cả");
    btnXoaTatCaProductionGroup.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCaProductionGroup.setOpaque(true);
    btnXoaTatCaProductionGroup.setBorderPainted(false);
    btnXoaTatCaProductionGroup.addActionListener(PGc);

    btnResetProductionGroup = new JButton("Làm Mới");
    btnResetProductionGroup.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnResetProductionGroup.setOpaque(true);
    btnResetProductionGroup.setBorderPainted(false);
    btnResetProductionGroup.addActionListener(PGc);

    // Thêm các nút vào panel
    buttonPanel.add(btnThemProductionGroup);
    buttonPanel.add(btnSuaProductionGroup);
    buttonPanel.add(btnXoaProductionGroup);
    buttonPanel.add(btnXoaTatCaProductionGroup);
    buttonPanel.add(btnResetProductionGroup);
    btnSuaProductionGroup.setEnabled(false);
    btnXoaProductionGroup.setEnabled(false);

    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);  // Thêm bottomPanel vào panel chính

    return panel;
  }


  JTable tableWarehouseStaff;
  private DefaultTableModel tableModelWarehouseStaff;
  JTextField TKField;
  JButton btnThemWarehouseStaff;
  JButton btnSuaWarehouseStaff;
  JButton btnXoaWarehouseStaff;
  JButton btnXoaTatCaWarehouseStaff;
  JButton btnResetWarehouseStaff;
  private JPanel createThuKhoPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    ActionListener WSc = new WarehouseStaffController(this);

    tableWarehouseStaff = new JTable();
    updateTableDataWarehouseStaff();
    styleTable2(tableWarehouseStaff);
    JScrollPane scrollPane = new JScrollPane(tableWarehouseStaff);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel PanelD = new JPanel(new GridLayout(1, 1, 6, 0));

    // Panel1
    JPanel Panel1 = new JPanel();
    Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));  // Sử dụng BoxLayout theo chiều dọc
    Panel1.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel Label1 = new JLabel("Thủ Kho");
    TKField = new JTextField();
    TKField.setFont(new Font("Arial", Font.BOLD, 14));
    Label1.setAlignmentX(Component.LEFT_ALIGNMENT);
    TKField.setAlignmentX(Component.LEFT_ALIGNMENT);
    Panel1.add(Label1);
    Panel1.add(TKField);

    PanelD.add(Panel1);


    // Tạo panel chứa PanelD và buttonPanel
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(PanelD, BorderLayout.CENTER);

    // Các nút chức năng
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Đặt FlowLayout.LEFT để căn chỉnh nút sang trái
// Tạo các nút và thêm màu nền
    btnThemWarehouseStaff = new JButton("Thêm");
    btnThemWarehouseStaff.setBackground(new Color(152, 201, 226,255)); // Màu xanh lá
    btnThemWarehouseStaff.setOpaque(true);  // Đảm bảo màu nền được hiển thị
    btnThemWarehouseStaff.setBorderPainted(false);  // Bỏ viền của nút
    btnThemWarehouseStaff.addActionListener(WSc);

    btnSuaWarehouseStaff = new JButton("Sửa");
    btnSuaWarehouseStaff.setBackground(new Color(152, 201, 226,255)); // Màu xanh dương
    btnSuaWarehouseStaff.setOpaque(true);
    btnSuaWarehouseStaff.setBorderPainted(false);
    btnSuaWarehouseStaff.addActionListener(WSc);

    btnXoaWarehouseStaff = new JButton("Xóa");
    btnXoaWarehouseStaff.setBackground(new Color(152, 201, 226,255)); // Màu đỏ
    btnXoaWarehouseStaff.setOpaque(true);
    btnXoaWarehouseStaff.setBorderPainted(false);
    btnXoaWarehouseStaff.addActionListener(WSc);

    btnXoaTatCaWarehouseStaff = new JButton("Xóa Tất Cả");
    btnXoaTatCaWarehouseStaff.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnXoaTatCaWarehouseStaff.setOpaque(true);
    btnXoaTatCaWarehouseStaff.setBorderPainted(false);
    btnXoaTatCaWarehouseStaff.addActionListener(WSc);

    btnResetWarehouseStaff = new JButton("Làm Mới");
    btnResetWarehouseStaff.setBackground(new Color(152, 201, 226,255)); // Màu cam
    btnResetWarehouseStaff.setOpaque(true);
    btnResetWarehouseStaff.setBorderPainted(false);
    btnResetWarehouseStaff.addActionListener(WSc);

    // Thêm các nút vào panel
    buttonPanel.add(btnThemWarehouseStaff);
    buttonPanel.add(btnSuaWarehouseStaff);
    buttonPanel.add(btnXoaWarehouseStaff);
    buttonPanel.add(btnXoaTatCaWarehouseStaff);
    buttonPanel.add(btnResetWarehouseStaff);
    btnSuaWarehouseStaff.setEnabled(false);
    btnXoaWarehouseStaff.setEnabled(false);

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
    gbc.anchor = GridBagConstraints.NORTHWEST; // Căn lên trên cùng bên trái
    gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng cột

    // Thêm các thành phần vào form
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.1;
    JLabel thuMucLabel = new JLabel("THƯ MỤC LƯU:");
    thuMucLabel.setFont(new Font("Arial", Font.BOLD, 15));
    panel.add(thuMucLabel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JTextField thuMucField = new JTextField("D:\\Export", 10);
    thuMucField.setFont(new Font("Arial", Font.PLAIN, 17));
    panel.add(thuMucField, gbc);

    // Thêm các thành phần vào form

// Cấu hình JLabel và JTextField cho "TÊN FILE"
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.1;
    JLabel tenFileLabel = new JLabel("TÊN FILE:");
    tenFileLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Thay đổi cỡ chữ của JLabel
    panel.add(tenFileLabel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JTextField tenFileField = new JTextField("Export.xlsx", 10);
    tenFileField.setFont(new Font("Arial", Font.PLAIN, 17)); // Thay đổi cỡ chữ của JTextField
    panel.add(tenFileField, gbc);

// Cấu hình JLabel và JTextField cho "NGƯỠNG CẢN"
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0.1;
    JLabel nguongCanLabel = new JLabel("NGƯỠNG CẢN:");
    nguongCanLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Thay đổi cỡ chữ của JLabel
    panel.add(nguongCanLabel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JTextField nguongCanField = new JTextField("10.00", 10);
    nguongCanField.setFont(new Font("Arial", Font.PLAIN, 17)); // Thay đổi cỡ chữ của JTextField
    panel.add(nguongCanField, gbc);

// Cấu hình JLabel và JComboBox cho "MÁY IN"
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.weightx = 0.1;
    JLabel mayInLabel = new JLabel("MÁY IN:");
    mayInLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Thay đổi cỡ chữ của JLabel
    panel.add(mayInLabel, gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.9;
    JComboBox<String> mayInComboBox = new JComboBox<>(new String[] {
        "Foxit PhantomPDF Printer", "Microsoft Print to PDF", "Canon LBP6030"
    });

    mayInComboBox.setFont(new Font("Arial", Font.PLAIN, 17)); // Cỡ chữ 17, kiểu chữ bình thường

    panel.add(mayInComboBox, gbc);


    // Spacer để đẩy toàn bộ các thành phần lên trên
    gbc.gridx = 0;
    gbc.gridy = 4; // Dòng cuối cùng
    gbc.weighty = 1.0; // Chiếm không gian dọc còn lại
    gbc.gridwidth = 2; // Kéo dài qua 2 cột
    panel.add(Box.createVerticalGlue(), gbc);

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





  public void updateTableData() {
    DaoProduct daoProduct = new DaoProduct();
    List<Product> products = daoProduct.selectAll(); // Lấy danh sách sản phẩm từ cơ sở dữ liệu

    // Chuyển đổi danh sách thành mảng hai chiều
    Object[][] data = new Object[products.size()][5]; // Thêm cột ẩn ProductID
    Object[][] originalData = new Object[products.size()][5]; // Lưu dữ liệu ban đầu

    for (int i = 0; i < products.size(); i++) {
      Product product = products.get(i);
      data[i][0] = i + 1; // STT
      data[i][1] = product.getProductName(); // Tên Đơn Hàng
      data[i][2] = product.getHSD(); // Hạn Sử Dụng
      data[i][3] = product.getColor(); // Màu
      data[i][4] = product.getProductID(); // ProductID (ẩn)

      originalData[i][0] = data[i][0];
      originalData[i][1] = data[i][1];
      originalData[i][2] = data[i][2];
      originalData[i][3] = data[i][3];
      originalData[i][4] = data[i][4];
    }

    // Cập nhật model cho JTable
    tableModel = new DefaultTableModel(data, new Object[]{"STT", "Tên Đơn Hàng", "Hạn Sử Dụng", "Màu", "ProductID"}) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0 && column != 3 && column != 4; // Không cho phép chỉnh sửa cột STT, Màu và ProductID
      }
    };
    tableMaHang.setModel(tableModel);

    // Ẩn cột ProductID
    tableMaHang.getColumnModel().getColumn(4).setMinWidth(0);
    tableMaHang.getColumnModel().getColumn(4).setMaxWidth(0);
    tableMaHang.getColumnModel().getColumn(4).setWidth(0);

    styleTable(tableMaHang);

    // Đăng ký TableModelListener để nhận sự kiện thay đổi dữ liệu
    tableModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();

          // Chỉ xử lý khi sửa cột "Tên Đơn Hàng" hoặc "Hạn Sử Dụng"
          if (column == 1 || column == 2) {
            String updatedValue = tableModel.getValueAt(row, column).toString();
            String originalValue = originalData[row][column].toString();

            // Kiểm tra xem giá trị mới có khác giá trị cũ không
            if (!updatedValue.equals(originalValue)) {
              Integer productID = (Integer) tableModel.getValueAt(row, 4);

              // Kiểm tra các trường nhập liệu
              if (column == 1 && updatedValue.isEmpty()) {
                DialogHelper.alert(null, "Vui lòng nhập Tên Đơn Hàng trong Bảng!");
                return;
              }

              if (column == 2) {
                try {
                  Double.parseDouble(updatedValue); // Kiểm tra giá trị HSD là số
                } catch (NumberFormatException ex) {
                  DialogHelper.alert(null, "Hạn Sử Dụng phải là một số hợp lệ!");
                  return;
                }
              }

              // Cập nhật dữ liệu
              Product updatedProduct = new Product(
                      productID,
                      tableModel.getValueAt(row, 1).toString(),
                      Double.parseDouble(tableModel.getValueAt(row, 2).toString()),
                      tableModel.getValueAt(row, 3).toString()
              );

              daoProduct.update(updatedProduct); // Gọi phương thức update từ DAO

              // Cập nhật giá trị ban đầu
              originalData[row][column] = updatedValue;
              DialogHelper.alert(null, "Dữ liệu đã được cập nhật.");
              updateTableData(); // Cập nhật lại bảng
              ResetFormMaHang();
            }
          }
        }
      }
    });

    // Thêm sự kiện lắng nghe khi người dùng chọn dòng
    tableMaHang.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting() && tableMaHang.getSelectedRow() != -1) {
        btnThemMaHang.setEnabled(false);
        btnXoaMaHang.setEnabled(true);
        btnSuaMaHang.setEnabled(true);
        btnXoaTatCaMaHang.setEnabled(false);
        btnResetMaHang.setEnabled(true);

        int selectedRow = tableMaHang.getSelectedRow();
        Integer productID = (Integer) tableModel.getValueAt(selectedRow, 4);

        // Lấy sản phẩm từ cơ sở dữ liệu
        Product selectedProduct = daoProduct.selectbyID(productID);

        if (selectedProduct != null) {
          productSendController = selectedProduct;
          DHField.setText(selectedProduct.getProductName());
          HSDField.setText(selectedProduct.getHSD().toString());
          ThukhoComboBox.setSelectedItem(selectedProduct.getColor());
        }
      }
    });
  }



  public void updateTableDataShift() {
    DaoShift daoShift = new DaoShift();
    List<Shift> shifts = daoShift.selectAll(); // Lấy danh sách shifts từ cơ sở dữ liệu

    // Chuyển đổi danh sách thành mảng hai chiều
    Object[][] data = new Object[shifts.size()][3]; // Thêm cột ẩn ShiftID
    Object[][] originalData = new Object[shifts.size()][3]; // Lưu dữ liệu ban đầu

    for (int i = 0; i < shifts.size(); i++) {
      Shift shift = shifts.get(i);
      data[i][0] = i + 1; // STT
      data[i][1] = shift.getShiftName(); // Tên Ca Sản Xuất
      data[i][2] = shift.getShiftId(); // ShiftID (ẩn)

      originalData[i][0] = data[i][0];
      originalData[i][1] = data[i][1];
      originalData[i][2] = data[i][2];
    }

    // Cập nhật model cho JTable
    tableModelShift = new DefaultTableModel(data, new Object[]{"STT", "Ca Sản Xuất", "ShiftID"}) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0; // Không cho phép chỉnh sửa cột STT
      }
    };
    tableShift.setModel(tableModelShift);

    // Ẩn cột ShiftID
    tableShift.getColumnModel().getColumn(2).setMinWidth(0);
    tableShift.getColumnModel().getColumn(2).setMaxWidth(0);
    tableShift.getColumnModel().getColumn(2).setWidth(0);

    styleTable2(tableShift);

    // Đăng ký TableModelListener để nhận sự kiện thay đổi dữ liệu
    tableModelShift.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();

          // Chỉ xử lý khi sửa cột "Ca Sản Xuất"
          if (column == 1) {
            String updatedValue = tableModelShift.getValueAt(row, column).toString();
            String originalValue = originalData[row][column].toString();

            // Kiểm tra xem giá trị mới có khác giá trị cũ không
            if (!updatedValue.equals(originalValue)) {
              Integer shiftId = (Integer) tableModelShift.getValueAt(row, 2);

              // Kiểm tra các trường nhập liệu
              if (updatedValue.isEmpty()) {
                DialogHelper.alert(null, "Vui lòng nhập Ca Sản Xuất trong Bảng!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
              }

              // Cập nhật dữ liệu
              Shift updatedShift = new Shift(shiftId, updatedValue);
              daoShift.update(updatedShift); // Gọi phương thức update từ DAO

              // Cập nhật giá trị ban đầu
              originalData[row][column] = updatedValue;
              DialogHelper.alert(null, "Dữ liệu đã được cập nhật.");
              updateTableDataShift(); // Tải lại bảng sau khi cập nhật
              ResetFormShift(); // Reset form nếu cần
            }
          }
        }
      }
    });

    // Thêm sự kiện lắng nghe khi người dùng chọn dòng
    tableShift.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting() && tableShift.getSelectedRow() != -1) {
        btnThemShift.setEnabled(false);
        btnXoaShift.setEnabled(true);
        btnSuaShift.setEnabled(true);
        btnXoaTatCaShift.setEnabled(false);
        btnResetShift.setEnabled(true);

        // Lấy chỉ số dòng được chọn
        int selectedRow = tableShift.getSelectedRow();

        // Lấy ShiftID từ cột ẩn
        Integer shiftId = (Integer) tableModelShift.getValueAt(selectedRow, 2);

        // Lấy Shift từ cơ sở dữ liệu
        Shift shift = daoShift.selectbyID(shiftId);

        if (shift != null) {
          ShiftSendController = shift;
          CSXShiftField.setText(shift.getShiftName());
        }
      }
    });
  }



  public void updateTableDataProductionGroup() {
    DaoProductionGroup daoproductionGroup = new DaoProductionGroup();
    List<ProductionGroup> productionGroups = daoproductionGroup.selectAll(); // Lấy danh sách shifts từ cơ sở dữ liệu

    // Chuyển đổi danh sách thành mảng hai chiều
    Object[][] data = new Object[productionGroups.size()][3]; // Thêm cột ẩn ShiftID
    Object[][] originalData = new Object[productionGroups.size()][3]; // Lưu dữ liệu ban đầu

    for (int i = 0; i < productionGroups.size(); i++) {
      ProductionGroup productionGroup = productionGroups.get(i);
      data[i][0] = i + 1; // STT
      data[i][1] = productionGroup.getGroupName(); // Tên Ca Sản Xuất
      data[i][2] = productionGroup.getGroupID(); // GroupID (ẩn)

      originalData[i][0] = data[i][0];
      originalData[i][1] = data[i][1];
      originalData[i][2] = data[i][2];
    }

    // Cập nhật model cho JTable
    tableModelProductionGroup = new DefaultTableModel(data, new Object[]{"STT", "Tổ Sản Xuất", "grID"}) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0; // Không cho phép chỉnh sửa cột STT
      }
    };
    tableProductionGroup.setModel(tableModelProductionGroup);

    // Ẩn cột ShiftID
    tableProductionGroup.getColumnModel().getColumn(2).setMinWidth(0);
    tableProductionGroup.getColumnModel().getColumn(2).setMaxWidth(0);
    tableProductionGroup.getColumnModel().getColumn(2).setWidth(0);

    styleTable2(tableProductionGroup);

    // Đăng ký TableModelListener để nhận sự kiện thay đổi dữ liệu
    tableModelProductionGroup.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();

          // Chỉ xử lý khi sửa cột "Ca Sản Xuất"
          if (column == 1) {
            String updatedValue = tableModelProductionGroup.getValueAt(row, column).toString();
            String originalValue = originalData[row][column].toString();

            // Kiểm tra xem giá trị mới có khác giá trị cũ không
            if (!updatedValue.equals(originalValue)) {
              Integer ProductionGrId = (Integer) tableModelProductionGroup.getValueAt(row, 2);

              // Kiểm tra các trường nhập liệu
              if (updatedValue.isEmpty()) {
                DialogHelper.alert(null, "Vui lòng nhập Tổ Sản Xuất trong Bảng!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
              }

              // Cập nhật dữ liệu
              ProductionGroup updatedProductionGroup= new ProductionGroup(ProductionGrId, updatedValue);
              daoproductionGroup.update(updatedProductionGroup); // Gọi phương thức update từ DAO

              // Cập nhật giá trị ban đầu
              originalData[row][column] = updatedValue;
              DialogHelper.alert(null, "Dữ liệu đã được cập nhật.");
              updateTableDataProductionGroup(); // Tải lại bảng sau khi cập nhật
              ResetFormProductionGroup(); // Reset form nếu cần
            }
          }
        }
      }
    });

    // Thêm sự kiện lắng nghe khi người dùng chọn dòng
    tableProductionGroup.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting() && tableProductionGroup.getSelectedRow() != -1) {
        btnThemProductionGroup.setEnabled(false);
        btnXoaProductionGroup.setEnabled(true);
        btnSuaProductionGroup.setEnabled(true);
        btnXoaTatCaProductionGroup.setEnabled(false);
        btnResetProductionGroup.setEnabled(true);

        // Lấy chỉ số dòng được chọn
        int selectedRow = tableProductionGroup.getSelectedRow();

        // Lấy ShiftID từ cột ẩn
        Integer shiftId = (Integer) tableModelProductionGroup.getValueAt(selectedRow, 2);

        // Lấy Shift từ cơ sở dữ liệu
        ProductionGroup productionGroup = daoproductionGroup.selectbyID(shiftId);

        if (productionGroup != null) {
          ProductionGroupSendController = productionGroup;
          TSXField.setText(productionGroup.getGroupName());
        }
      }
    });
  }


  public void updateTableDataWarehouseStaff() {
    DaoWarehouseStaff daoWarehouseStaff = new DaoWarehouseStaff();
    List<WarehouseStaff> warehouseStaffs = daoWarehouseStaff.selectAll(); // Lấy danh sách shifts từ cơ sở dữ liệu

    // Chuyển đổi danh sách thành mảng hai chiều
    Object[][] data = new Object[warehouseStaffs.size()][3]; // Thêm cột ẩn ShiftID
    Object[][] originalData = new Object[warehouseStaffs.size()][3]; // Lưu dữ liệu ban đầu

    for (int i = 0; i < warehouseStaffs.size(); i++) {
      WarehouseStaff productionGroup = warehouseStaffs.get(i);
      data[i][0] = i + 1; // STT
      data[i][1] = productionGroup.getStaffName(); // Tên Ca Sản Xuất
      data[i][2] = productionGroup.getStaffId(); // GroupID (ẩn)

      originalData[i][0] = data[i][0];
      originalData[i][1] = data[i][1];
      originalData[i][2] = data[i][2];
    }

    // Cập nhật model cho JTable
    tableModelWarehouseStaff = new DefaultTableModel(data, new Object[]{"STT", "Tổ Sản Xuất", "grID"}) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0; // Không cho phép chỉnh sửa cột STT
      }
    };
    tableWarehouseStaff.setModel(tableModelWarehouseStaff);

    // Ẩn cột ShiftID
    tableWarehouseStaff.getColumnModel().getColumn(2).setMinWidth(0);
    tableWarehouseStaff.getColumnModel().getColumn(2).setMaxWidth(0);
    tableWarehouseStaff.getColumnModel().getColumn(2).setWidth(0);

    styleTable2(tableWarehouseStaff);

    // Đăng ký TableModelListener để nhận sự kiện thay đổi dữ liệu
    tableModelWarehouseStaff.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();

          // Chỉ xử lý khi sửa cột "Ca Sản Xuất"
          if (column == 1) {
            String updatedValue = tableModelWarehouseStaff.getValueAt(row, column).toString();
            String originalValue = originalData[row][column].toString();

            // Kiểm tra xem giá trị mới có khác giá trị cũ không
            if (!updatedValue.equals(originalValue)) {
              Integer WarehouseStaffId = (Integer) tableModelWarehouseStaff.getValueAt(row, 2);

              // Kiểm tra các trường nhập liệu
              if (updatedValue.isEmpty()) {
                DialogHelper.alert(null, "Vui lòng nhập Thủ Kho trong Bảng!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
              }

              // Cập nhật dữ liệu
              WarehouseStaff updatedWarehouseStaff = new WarehouseStaff(WarehouseStaffId, updatedValue);
              daoWarehouseStaff.update(updatedWarehouseStaff); // Gọi phương thức update từ DAO

              // Cập nhật giá trị ban đầu
              originalData[row][column] = updatedValue;
              DialogHelper.alert(null, "Dữ liệu đã được cập nhật.");
              updateTableDataWarehouseStaff(); // Tải lại bảng sau khi cập nhật
              ResetFormWarehouseStaff(); // Reset form nếu cần
            }
          }
        }
      }
    });

    // Thêm sự kiện lắng nghe khi người dùng chọn dòng
    tableWarehouseStaff.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting() && tableWarehouseStaff.getSelectedRow() != -1) {
        btnThemWarehouseStaff.setEnabled(false);
        btnXoaWarehouseStaff.setEnabled(true);
        btnSuaWarehouseStaff.setEnabled(true);
        btnXoaTatCaWarehouseStaff.setEnabled(false);
        btnResetWarehouseStaff.setEnabled(true);

        // Lấy chỉ số dòng được chọn
        int selectedRow = tableWarehouseStaff.getSelectedRow();

        // Lấy ShiftID từ cột ẩn
        Integer shiftId = (Integer) tableModelWarehouseStaff.getValueAt(selectedRow, 2);

        // Lấy Shift từ cơ sở dữ liệu
        WarehouseStaff warehouseStaff = daoWarehouseStaff.selectbyID(shiftId);

        if (warehouseStaff != null) {
          WarehouseStaffSendController = warehouseStaff;
          TKField.setText(warehouseStaff.getStaffName());
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

  //Product
  public JTextField getDHField() {
    return DHField;
  }

  public JTextField getHSDField() {
    return HSDField;
  }

  public JComboBox<String> getThukhoComboBox() {
    return ThukhoComboBox;
  }

  public JButton getbtnThemMaHang() {
    return btnThemMaHang;
  }

  public JButton getbtnSuaMaHang() {
    return btnSuaMaHang;
  }

  public JButton getbtnXoaMaHang() {
    return btnXoaMaHang;
  }

  public JButton getbtnXoaTatCaMaHang() {
    return btnXoaTatCaMaHang;
  }

  public JButton getbtnResetMaHang() {
    return btnResetMaHang;
  }

  public Product getProductSendControllerg() {
    return productSendController;
  }

  public void ResetFormMaHang () {
    getDHField().setText("");
    getHSDField().setText("");
    getThukhoComboBox().setSelectedIndex(0);
    getbtnThemMaHang().setEnabled(true);
    getbtnSuaMaHang().setEnabled(false);
    getbtnXoaMaHang().setEnabled(false);
    getbtnXoaTatCaMaHang().setEnabled(true);
    productSendController = null;
  }


  //Shift

  public JTextField getCSXShiftField() {
    return CSXShiftField;
  }

  public JButton getbtnThemShift() {
    return btnThemShift;
  }

  public JButton getbtnSuaShift() {
    return btnSuaShift;
  }

  public JButton getbtnXoaShift() {
    return btnXoaShift;
  }

  public JButton getbtnXoaTatCaShift() {
    return btnXoaTatCaShift;
  }

  public JButton getbtnResetShift() {
    return btnResetShift;
  }

  public Shift getShiftSendControllerg() {
    return ShiftSendController;
  }


  public void ResetFormShift () {
   getCSXShiftField().setText("");
    getbtnThemShift().setEnabled(true);
   getbtnSuaShift().setEnabled(false);
   getbtnXoaShift().setEnabled(false);
   getbtnXoaTatCaShift().setEnabled(true);
  }


  //ProducGroup
  public JTextField getTSXField() {
    return TSXField;
  }

  public JButton getbtnThemProductionGroup() {
    return btnThemProductionGroup;
  }

  public JButton getbtnSuaProductionGroup() {
    return btnSuaProductionGroup;
  }

  public JButton getbtnXoaProductionGroup() {
    return btnXoaProductionGroup;
  }

  public JButton getbtnXoaTatCaProductionGroup() {
    return btnXoaTatCaProductionGroup;
  }

  public JButton getbtnResetProductionGroup() {
    return btnResetProductionGroup;
  }

  public ProductionGroup getProductionGroupSendController() {
    return ProductionGroupSendController;
  }


  public void ResetFormProductionGroup () {
    getTSXField().setText("");
    getbtnThemProductionGroup().setEnabled(true);
    getbtnSuaProductionGroup().setEnabled(false);
    getbtnXoaProductionGroup().setEnabled(false);
    getbtnXoaTatCaProductionGroup().setEnabled(true);
  }

  //WareHouseStaff
  public JTextField getTKField() {
    return TKField;
  }

  public JButton getbtnThemWarehouseStaff() {
    return btnThemWarehouseStaff;
  }

  public JButton getbtnSuaWarehouseStaff() {
    return btnSuaWarehouseStaff;
  }

  public JButton getbtnXoaWarehouseStaff() {
    return btnXoaWarehouseStaff;
  }

  public JButton getbtnXoaTatCaWarehouseStaff() {
    return btnXoaTatCaWarehouseStaff;
  }

  public JButton getbtnResetWarehouseStaff() {
    return btnResetWarehouseStaff;
  }

  public WarehouseStaff getWarehouseStaffSendController() {
    return WarehouseStaffSendController;
  }


  public void ResetFormWarehouseStaff() {
    getTKField().setText("");
    getbtnThemWarehouseStaff().setEnabled(true);
    getbtnSuaWarehouseStaff().setEnabled(false);
    getbtnXoaWarehouseStaff().setEnabled(false);
    getbtnXoaTatCaWarehouseStaff().setEnabled(true);
  }





}

