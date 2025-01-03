package view;

import Utils.DialogHelper;
import Utils.RoundedBorder;
import com.toedter.calendar.JDateChooser;
import controller.HomeController;
import dao.*;
import model.Lot;
import model.Pallet;
import model.Product;
import model.SettingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePanel extends JPanel {
  public DaoProduct daoProduct;
  public DaoProductionGroup DaoProductionGroup;
  public DaoShift daoShift;
  public DaoWarehouseStaff daoWarehouseStaff;
  public DaoSettingSystem daoSettingSystem;
  private JPopupMenu popupMenu;
  HomeController homeController = new HomeController(this);
  public Lot lotSendController;
  JButton button4 = new JButton();
  public HomePanel() {
    setLayout(new BorderLayout());

    // Tạo container phía trên
    add(createSoftwarePanel(), BorderLayout.NORTH);

    // Tạo phần header panel với các phần (Left, Center, Right)
    add(createHeaderPanel(), BorderLayout.CENTER);

    // Thêm bảng JTable
    add(createTablePanel(), BorderLayout.SOUTH);


    // Tạo và gắn menu chuột phải
    createContextMenu();
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        showPopupMenu(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        showPopupMenu(e);
      }
    });


  }

  private void createContextMenu() {
    popupMenu = new JPopupMenu();

    JMenuItem menuItem1 = new JMenuItem("Refresh");
    menuItem1.addActionListener(e -> {
      refreshComboBoxData();
      refreshprinter();
      RefreshT();
      refreshToSXComboBoxData();
      refreshcaSanxuatComboBoxData();
      refreshThukhoComboBoxData();
      updateTableLot();
      JOptionPane.showMessageDialog(this, "Trang Chủ Đã Làm Mới!");
    });

    JMenuItem menuItem2 = new JMenuItem("Đóng");



    popupMenu.add(menuItem1);
    popupMenu.addSeparator(); // Dòng ngăn cách
    popupMenu.add(menuItem2);
  }

  private void showPopupMenu(MouseEvent e) {
    if (e.isPopupTrigger()) { // Kiểm tra nếu là chuột phải
      popupMenu.show(this, e.getX(), e.getY());
    }
  }


  // Phương thức tạo panel tiêu đề phần mềm
  private JPanel createSoftwarePanel() {
    JPanel softwarePanel = new JPanel();
    softwarePanel.setBackground(Color.WHITE);
    softwarePanel.setBorder(new RoundedBorder(0));

    JLabel softwareLabel = new JLabel("PHẦN MỀM CÂN BÁN THÀNH PHẨM");
    softwareLabel.setVerticalAlignment(SwingConstants.TOP);
    softwareLabel.setFont(new Font("Arial", Font.BOLD, 16));
    softwarePanel.add(softwareLabel);

    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBackground(Color.WHITE);
    containerPanel.setPreferredSize(new Dimension(0, 40));
    containerPanel.add(softwarePanel, BorderLayout.CENTER);

    return containerPanel;
  }

  // Phương thức tạo panel header (Left, Center, Right)
  private JPanel createHeaderPanel() {
    JPanel headerPanel = new JPanel(new GridLayout(1, 3, 6, 0));
    headerPanel.setBorder(new EmptyBorder(6, 4, 0, 4));
    headerPanel.add(createLeftPanel());
    headerPanel.add(createCenterPanel());
    headerPanel.add(createRightPanel());

    return headerPanel;
  }

  // Panel bên trái
  private JComboBox<String> maHangComboBox;
  JTextField mauField;
  JTextField hanSDField;
  JTextField soLoField;
  JTextField klBiField;
  JComboBox<String> printerBox;
  JButton printButton;
  private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridBagLayout());
    leftPanel.setBorder(new RoundedBorder(20));
    leftPanel.setBackground(Color.WHITE);


    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(3, 5, 3, 5); // Khoảng cách giữa các thành phần
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;

    // Tiêu đề (căn giữa)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // Tiêu đề chiếm 2 cột
    JLabel titleLabel = new JLabel("THÔNG TIN ĐƠN HÀNG", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
    leftPanel.add(titleLabel, gbc);

    // Mã Hàng
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1; // Reset về 1 cột
    gbc.anchor = GridBagConstraints.WEST; // Căn trái
    JLabel maHangLabel = new JLabel("Mã Hàng:");
    maHangLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(maHangLabel, gbc);


    gbc.gridx = 1;
    maHangComboBox = new JComboBox<>(); // Khởi tạo JComboBox
    maHangComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    refreshComboBoxData(); // Gọi phương thức làm mới dữ liệu khi khởi tạo
    leftPanel.add(maHangComboBox, gbc);
    maHangComboBox.addActionListener(homeController);
    // Màu
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel mauLabel = new JLabel("Màu Giấy In:");
    mauLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(mauLabel, gbc);

    gbc.gridx = 1;
    mauField = new JTextField();
    mauField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    mauField.setBackground(new Color(255, 255, 204)); // Màu vàng nhạt
    mauField.setEditable(false);
    leftPanel.add(mauField, gbc);

    // Hạn SD
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel hanSDLabel = new JLabel("Hạn Sử Dụng(Ngày):");
    hanSDLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(hanSDLabel, gbc);

    gbc.gridx = 1;
     hanSDField = new JTextField("0");
    hanSDField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    hanSDField.setBackground(new Color(255, 255, 204)); // Màu vàng nhạt
    hanSDField.setEditable(false);
    leftPanel.add(hanSDField, gbc);

    // Số Lô
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel soLoLabel = new JLabel("Số Lô:");
    soLoLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(soLoLabel, gbc);

    gbc.gridx = 1;
    soLoField = new JTextField();
    soLoField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    leftPanel.add(soLoField, gbc);

    // KL Bi
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel klBiLabel = new JLabel("Khối Lượng BÌ (Kg):");
    klBiLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(klBiLabel, gbc);

    gbc.gridx = 1;
     klBiField = new JTextField("0");
    klBiField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    leftPanel.add(klBiField, gbc);

    // Printer
    gbc.gridx = 0;
    gbc.gridy = 6;
    JLabel printerLabel = new JLabel("Printer:");
    printerLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(printerLabel, gbc);

    gbc.gridx = 1;
    JPanel printerPanel = new JPanel(new BorderLayout());
    printerBox = new JComboBox<>();
    refreshprinter();
    printButton = new JButton();
    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon = new javax.swing.ImageIcon(getClass().getResource("/img/printer2.png"));
    Image iconImage = originalIcon.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage = iconImage.getScaledInstance(23, 23, Image.SCALE_SMOOTH);
    printButton.setBackground(new Color(71,138,173,255));
    printButton.setIcon(new ImageIcon(scaledImage));
    printButton.setForeground(Color.WHITE);
    printButton.setEnabled(false);
    printerPanel.add(printerBox, BorderLayout.CENTER);
    printerPanel.add(printButton, BorderLayout.EAST);
    leftPanel.add(printerPanel, gbc);

    return leftPanel;
  }

  // Hàm làm mới dữ liệu cho JComboBox
  public void refreshComboBoxData() {
    daoProduct = new DaoProduct();
    List<String> productNames = daoProduct.getAllProductNames(); // Lấy danh sách mới từ DAO

    if(productNames.size() > 0) {
      // Xóa tất cả các mục cũ
      maHangComboBox.removeAllItems();
      maHangComboBox.addItem("Mã Hàng");
      // Thêm các mục mới
      for (String name : productNames) {
        maHangComboBox.addItem(name);
      }
    }
    else {
      maHangComboBox.addItem("Chưa Có Mã Hàng Nào");
    }

  }

  // Hàm Cập nhật printer
  public void refreshprinter() {
    daoSettingSystem = new DaoSettingSystem();
    SettingSystem settingSystem = daoSettingSystem.selectLatest();
    if(settingSystem != null) {
      printerBox.removeAllItems();
      printerBox.addItem(settingSystem.getPrinter());
    }
  }


  // Panel trung tâm
  JComboBox<String> ToSXComboBox;
  JComboBox<String> caSanxuatComboBox;
  JComboBox<String> ThukhoComboBox;
  JTextField SoPalletTe;
  JDateChooser NSXDate;
  private JPanel createCenterPanel() {
    JPanel CentePanel = new JPanel();
    CentePanel.setLayout(new GridBagLayout());
    CentePanel.setBorder(new RoundedBorder(20));
    CentePanel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(3, 5, 2, 5); // Khoảng cách giữa các thành phần
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;

    // Tiêu đề (căn giữa)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // Tiêu đề chiếm 2 cột
    JLabel titleLabel = new JLabel("THÔNG TIN SẢN XUẤT", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
    CentePanel.add(titleLabel, gbc);

    // Mã Hàng
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1; // Reset về 1 cột
    gbc.anchor = GridBagConstraints.WEST; // Căn trái
    JLabel TSXLabel = new JLabel("Tổ Sản Xuất:");
    TSXLabel.setFont(new Font("Arial", Font.BOLD, 14));
    CentePanel.add(TSXLabel, gbc);

    gbc.gridx = 1;
    ToSXComboBox = new JComboBox<>();
    ToSXComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    refreshToSXComboBoxData();
    CentePanel.add(ToSXComboBox, gbc);

    // Màu
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel CSXLabel = new JLabel("Ca Sản Xuất:");
    CSXLabel.setFont(new Font("Arial", Font.BOLD, 14));
    CentePanel.add(CSXLabel, gbc);

    gbc.gridx = 1;
    caSanxuatComboBox =  new JComboBox<>();
    caSanxuatComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    refreshcaSanxuatComboBoxData();
    CentePanel.add(caSanxuatComboBox, gbc);


    // Số Lô
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel tHUKHOLabel = new JLabel("Thủ Kho:");
    tHUKHOLabel.setFont(new Font("Arial", Font.BOLD, 14));
    CentePanel.add(tHUKHOLabel, gbc);

    gbc.gridx = 1;
    ThukhoComboBox = new JComboBox<>();
    ThukhoComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    refreshThukhoComboBoxData();
    CentePanel.add(ThukhoComboBox, gbc);

    // KL Bi
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel NSXLabel = new JLabel("Ngày Sản Xuất:");
    NSXLabel.setFont(new Font("Arial", Font.BOLD, 14));
    CentePanel.add(NSXLabel, gbc);

    gbc.gridx = 1;
     NSXDate = new JDateChooser();
    NSXDate.setDate(new Date());
    NSXDate.setDateFormatString("dd/MM/yyyy");
    CentePanel.add(NSXDate, gbc);

    // KL Bi
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel SoPalletLabel = new JLabel("Số Pallet:");
    SoPalletLabel.setFont(new Font("Arial", Font.BOLD, 14));
    CentePanel.add(SoPalletLabel, gbc);

    gbc.gridx = 1;
    SoPalletTe= new JTextField("");
    SoPalletTe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    CentePanel.add(SoPalletTe, gbc);


    // Printer
    gbc.gridx = 0;
    gbc.gridy = 6;
    JLabel printerLabel = new JLabel("Tìm mã hàng và số lô:");
    printerLabel.setFont(new Font("Arial", Font.BOLD, 13));
    CentePanel.add(printerLabel, gbc);

    gbc.gridx = 1;
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JTextField searchField = new JTextField(10);
    searchField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    JButton timButton = new JButton();
    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon = new javax.swing.ImageIcon(getClass().getResource("/img/search.png"));
    Image iconImage = originalIcon.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage = iconImage.getScaledInstance(14, 14, Image.SCALE_SMOOTH);
    timButton.setIcon(new ImageIcon(scaledImage));
    timButton.setBackground(new Color(71,138,173,255));

    JButton lamMoiButton = new JButton();
    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon2 = new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"));
    Image iconImage2 = originalIcon2.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage2 = iconImage2.getScaledInstance(14, 14, Image.SCALE_SMOOTH);
    lamMoiButton.setIcon(new ImageIcon(scaledImage2));
    lamMoiButton.setBackground(new Color(71,138,173,255));

    searchPanel.add(searchField);
    searchPanel.add(timButton);
    searchPanel.add(lamMoiButton);
    searchPanel.setBackground(Color.WHITE); // Đồng nhất màu nền
    CentePanel.add(searchPanel, gbc);

    CentePanel.add(searchPanel, gbc);

    return CentePanel;
  }

  // Hàm làm mới dữ liệu cho JComboBox
  public void refreshToSXComboBoxData() {
    DaoProductionGroup = new DaoProductionGroup();
    List<String> productNames = DaoProductionGroup.getAllProductionGroupNames(); // Lấy danh sách mới từ DAO
    if(productNames.size() > 0) {
      // Xóa tất cả các mục cũ
      ToSXComboBox.removeAllItems();
      ToSXComboBox.addItem("Tổ");
      // Thêm các mục mới
      for (String name : productNames) {
        ToSXComboBox.addItem(name);
      }
    }
    else {
      ToSXComboBox.addItem("Chưa Có Tổ Nào");
    }

  }

  // Hàm làm mới dữ liệu cho JComboBox
  public void refreshcaSanxuatComboBoxData() {
    daoShift = new DaoShift();
    List<String> productNames = daoShift.getAllShiftNames(); // Lấy danh sách mới từ DAO

   if(productNames.size() > 0) {
     // Xóa tất cả các mục cũ
     caSanxuatComboBox.removeAllItems();
     caSanxuatComboBox.addItem("Ca");
     // Thêm các mục mới
     for (String name : productNames) {
       caSanxuatComboBox.addItem(name);
     }
   }
   else {
     caSanxuatComboBox.addItem("Chưa Có Ca Nào");
   }
  }

  // Hàm làm mới dữ liệu cho JComboBox
  public void refreshThukhoComboBoxData() {
    daoWarehouseStaff = new DaoWarehouseStaff();
    List<String> productNames = daoWarehouseStaff.getAllWarehouseStaffNames(); // Lấy danh sách mới từ DAO

    if(productNames.size() > 0) {

      // Xóa tất cả các mục cũ
      ThukhoComboBox.removeAllItems();
      ThukhoComboBox.addItem("Thủ Kho");
      // Thêm các mục mới
      for (String name : productNames) {
        ThukhoComboBox.addItem(name);
      }
    }
    else {
      ThukhoComboBox.addItem("Chưa Có Thủ Kho Nào");
    }
  }




  //panel phai
  JLabel KLCLabel;
  JTextField KLTField;
  private JPanel createRightPanel() {
    JPanel RightPanel = new JPanel();
    RightPanel.setLayout(new GridBagLayout());
    RightPanel.setBorder(new RoundedBorder(20));
    RightPanel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 0, 0, 0); // Không có khoảng cách giữa các phần tử
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;

    // Tiêu đề (căn giữa)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // Tiêu đề chiếm 2 cột
    JLabel titleLabel = new JLabel("KHỐI LƯỢNG CÂN", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
    RightPanel.add(titleLabel, gbc);

    // Tạo TPanel chứa LPanel và RPanel
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2; // Reset về 1 cột
    gbc.anchor = GridBagConstraints.WEST; // Căn trái

    JPanel TPanel = new JPanel();
    TPanel.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout để dễ dàng điều chỉnh vị trí các panel con
    TPanel.setBackground(Color.white);
    GridBagConstraints innerGbc = new GridBagConstraints();
    innerGbc.insets = new Insets(0, 0, 0, 0); // Không có khoảng cách giữa các panel con
    innerGbc.fill = GridBagConstraints.HORIZONTAL;

    // Left Panel
    JPanel LPanel = new JPanel();
    LPanel.setBorder(new RoundedBorder(2));
    LPanel.setBackground(Color.WHITE);
    LPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout để căn chỉnh nội dung bên trong
    JLabel DonviLabel = new JLabel("Đơn Vị Tính:", SwingConstants.LEFT);
    DonviLabel.setFont(new Font("Arial", Font.BOLD, 12));
    LPanel.add(DonviLabel, BorderLayout.CENTER);

    innerGbc.gridx = 0;
    innerGbc.gridy = 0;
    innerGbc.weightx = 0.5; // Chia không gian đều giữa LPanel và RPanel
    innerGbc.weighty = 1;
    innerGbc.fill = GridBagConstraints.BOTH; // Mở rộng cả chiều ngang và dọc
    TPanel.add(LPanel, innerGbc);

// Right Panel
    JPanel RPanel = new JPanel();
    RPanel.setBorder(new RoundedBorder(2));
    RPanel.setBackground(Color.WHITE);
    RPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout để căn chỉnh nội dung bên trong
    JLabel KLLabel = new JLabel("Khối Lượng Cân Hiện Tại:", SwingConstants.LEFT);
    KLLabel.setFont(new Font("Arial", Font.BOLD, 12));
    RPanel.add(KLLabel, BorderLayout.CENTER);

    innerGbc.gridx = 1;
    innerGbc.gridy = 0;
    innerGbc.weightx = 0.5;
    innerGbc.weighty = 1;
    innerGbc.fill = GridBagConstraints.BOTH;
    TPanel.add(RPanel, innerGbc);

// LPane2
    JPanel LPane2 = new JPanel();
    LPane2.setBorder(new RoundedBorder(2));
    LPane2.setBackground(Color.WHITE);
    LPane2.setLayout(new BorderLayout());
    JLabel DonviLabel2 = new JLabel("Kg", SwingConstants.CENTER);
    DonviLabel2.setFont(new Font("Arial", Font.BOLD, 24));
    LPane2.add(DonviLabel2, BorderLayout.CENTER);

    innerGbc.gridx = 0;
    innerGbc.gridy = 1;
    innerGbc.weightx = 1;
    innerGbc.weighty = 2;
    innerGbc.fill = GridBagConstraints.BOTH;
    TPanel.add(LPane2, innerGbc);

// RPane2
    JPanel RPane2 = new JPanel();
    RPane2.setBorder(new RoundedBorder(2));
    RPane2.setBackground(Color.WHITE);
    RPane2.setLayout(new BorderLayout());
    KLCLabel = new JLabel("220.00", SwingConstants.CENTER);
    KLCLabel.setFont(new Font("Arial", Font.BOLD, 50));
    RPane2.add(KLCLabel, BorderLayout.CENTER);

    innerGbc.gridx = 1;
    innerGbc.gridy = 1;
    innerGbc.weightx = 1;
    innerGbc.weighty = 2;
    innerGbc.fill = GridBagConstraints.BOTH;
    TPanel.add(RPane2, innerGbc);

// Hàng Khối Lượng Tịnh
    JPanel KLTPanel = new JPanel();
    KLTPanel.setBackground(Color.WHITE);
    KLTPanel.setLayout(new BorderLayout());
    JLabel KLTLabel = new JLabel("Khối Lượng Tịnh (Kg):", SwingConstants.LEFT);
    KLTLabel.setFont(new Font("Arial", Font.BOLD, 14));
    KLTPanel.add(KLTLabel, BorderLayout.CENTER);

    innerGbc.gridx = 0;
    innerGbc.gridy = 2;
    innerGbc.weightx = 0.5;
    innerGbc.weighty = 1;
    innerGbc.fill = GridBagConstraints.BOTH;
    TPanel.add(KLTPanel, innerGbc);

// TextField Khối Lượng Tịnh
    JPanel KLTFieldPanel = new JPanel();
    KLTFieldPanel.setBackground(new Color(255, 228, 181)); // Màu nền vàng nhạt
    KLTFieldPanel.setLayout(new BorderLayout());
     KLTField = new JTextField();
    KLTField.setBackground(new Color(255, 255, 204)); // Màu vàng nhạt
    KLTField.setText("0.00");
    KLTField.setEditable(false);
    KLTField.setFont(new Font("Arial", Font.BOLD, 19));
    KLTFieldPanel.add(KLTField, BorderLayout.CENTER);

    innerGbc.gridx = 1;
    innerGbc.gridy = 2;
    innerGbc.weightx = 0.5;
    innerGbc.weighty = 1;
    innerGbc.fill = GridBagConstraints.BOTH;
    TPanel.add(KLTFieldPanel, innerGbc);

// Button XÁC NHẬN
    JPanel ButtonPanel = new JPanel();
    ButtonPanel.setLayout(new BorderLayout()); // Đảm bảo nút "XÁC NHẬN" chiếm hết diện tích panel
    JButton confirmButton = new JButton("XÁC NHẬN");
    confirmButton.setBackground(new Color(0, 128, 255)); // Màu nền xanh
    confirmButton.setForeground(Color.WHITE); // Chữ trắng
    confirmButton.setFont(new Font("Arial", Font.BOLD, 12));
    confirmButton.addActionListener(homeController);
    ButtonPanel.add(confirmButton, BorderLayout.CENTER);

    innerGbc.gridx = 0;
    innerGbc.gridy = 3;
    innerGbc.gridwidth = 2; // Nút chiếm 2 cột
    innerGbc.weightx = 0.5;
    innerGbc.weighty = 0.5;
    innerGbc.fill = GridBagConstraints.BOTH;
    TPanel.add(ButtonPanel, innerGbc);



    // Thêm TPanel vào RightPanel
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.weightx = 1; // Đảm bảo chiếm hết chiều ngang
    gbc.weighty = 1; // Thêm trọng số chiều dọc để mở rộng TPanel
    gbc.fill = GridBagConstraints.BOTH; // Mở rộng cả chiều ngang và dọc
    RightPanel.add(TPanel, gbc);

// Đặt kích thước tối thiểu cho TPanel nếu cần
    TPanel.setPreferredSize(new Dimension(0, 400)); // Đảm bảo chiều cao tối thiểu

    return RightPanel;
  }




  // Phương thức tạo bảng JTable
  DefaultTableModel modelTableLot;
  JTable tableLot;
  private JPanel createTablePanel() {
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton button1 = new JButton("Thêm");
    button1.setBackground(new Color(152, 201, 226, 255));
    button1.setOpaque(true);
    button1.setBorderPainted(false);

    JButton button2 = new JButton("Xóa");
    button2.setBackground(new Color(152, 201, 226, 255));
    button2.setOpaque(true);
    button2.setBorderPainted(false);

    JButton button3 = new JButton("Sửa");
    button3.setBackground(new Color(152, 201, 226, 255));
    button3.setOpaque(true);
    button3.setBorderPainted(false);

    button4 = new JButton("Nhập");
    button4.setBackground(new Color(152, 201, 226, 255));
    button4.setOpaque(true);
    button4.setBorderPainted(false);
    button4.setEnabled(false);

    // Thêm nút vào buttonPanel
    buttonPanel.add(button1);
    buttonPanel.add(button2);
    buttonPanel.add(button3);
    buttonPanel.add(button4);

    // Tạo bảng JTable
    tableLot = new JTable();
    updateTableLot();
    styleTable(tableLot);

    // Tạo JScrollPane cho bảng
    JScrollPane tableScrollPane = new JScrollPane(tableLot);

    // Tạo JPanel lớn hơn để chứa cả buttonPanel và bảng
    JPanel containerPanel = new JPanel();
    containerPanel.setLayout(new BorderLayout());

    // Thêm buttonPanel vào phía trên
    containerPanel.add(buttonPanel, BorderLayout.NORTH);

    // Thêm bảng vào giữa
    containerPanel.add(tableScrollPane, BorderLayout.SOUTH);

    // Trả về containerPanel
    return containerPanel;
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
    columnModel.getColumn(0).setPreferredWidth(40);  // Số phiếu
    columnModel.getColumn(1).setPreferredWidth(300); // Mã hàng
    columnModel.getColumn(2).setPreferredWidth(100); // Số lô
    columnModel.getColumn(3).setPreferredWidth(30);  // Tổ
    columnModel.getColumn(4).setPreferredWidth(30);  // Ca
    columnModel.getColumn(5).setPreferredWidth(60); // Thời gian SX
    columnModel.getColumn(6).setPreferredWidth(30);  // KL cân
    columnModel.getColumn(7).setPreferredWidth(30);  // KL bì
    columnModel.getColumn(8).setPreferredWidth(30);  // KL hàng
    columnModel.getColumn(9).setPreferredWidth(160); // Thủ kho
    columnModel.getColumn(10).setPreferredWidth(50); // HSD
    columnModel.getColumn(11).setPreferredWidth(70); // Số pallet

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

  public void updateTableLot() {
    DaoLot daoLot = new DaoLot();
    DaoPallet daoPallet = new DaoPallet();
    List<Lot> lots = daoLot.selectAll(); // Lấy danh sách lô từ cơ sở dữ liệu

    // Chuyển đổi danh sách thành mảng hai chiều
    Object[][] data = new Object[lots.size()][13]; // Thêm cột "LotID"
    Object[][] originalData = new Object[lots.size()][13]; // Lưu dữ liệu ban đầu

    for (int i = 0; i < lots.size(); i++) {
      Lot lot = lots.get(i);
      data[i][0] = i + 1; // STT
      data[i][1] = lot.getProduct().getProductName(); // Mã Hàng
      data[i][2] = lot.getLotIDU(); // Số Lô
      data[i][3] = lot.getProductionGroup().getGroupName(); // Tổ
      data[i][4] = lot.getShift().getShiftName(); // Ca
      data[i][5] = lot.getProductionTime(); // Thời Gian SX
      data[i][6] = lot.getWarehouseWeight(); // KL Cân
      data[i][7] = lot.getWeightDeviation(); // KL Bì
      data[i][8] = lot.getWeight(); // KL Hàng
      data[i][9] = lot.getWarehouseStaff().getStaffName(); // Thủ Kho
      data[i][10] = lot.getExpirationDate(); // HSD
      data[i][11] = daoPallet.selectByLotID(lot.getLotID()).getPalletIDU(); // Số Pallet
      data[i][12] = lot.getLotID(); // LotID (ẩn)

      System.arraycopy(data[i], 0, originalData[i], 0, data[i].length);
    }

    // Định nghĩa DefaultTableModel với đầy đủ cột, bao gồm cả "LotID" (ẩn)
    modelTableLot = new DefaultTableModel(data, new Object[]{
            "Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân",
            "KL Bì", "KL Tịnh", "Thủ Kho", "HSD", "Số Pallet", "LotID"
    }) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0 && column != 1 && column != 3 && column != 4 && column != 5 && column != 6 && column != 8 && column != 9 && column != 10 && column != 12; // Không cho phép chỉnh sửa cột STT và LotID
      }
    };

    tableLot.setModel(modelTableLot);

    // Ẩn cột "LotID"
    tableLot.getColumnModel().getColumn(12).setMinWidth(0);
    tableLot.getColumnModel().getColumn(12).setMaxWidth(0);
    tableLot.getColumnModel().getColumn(12).setWidth(0);

    styleTable(tableLot); // Gọi hàm styleTable để định dạng bảng

    // Đăng ký TableModelListener để xử lý sự kiện thay đổi dữ liệu
    modelTableLot.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
          int column = e.getColumn();

          // Chỉ xử lý khi sửa cột cần thiết
          if (column == 2 || column == 7 || column == 11) {
            String updatedValue = modelTableLot.getValueAt(row, column).toString();
            String originalValue = originalData[row][column].toString();

            // Kiểm tra xem giá trị mới có khác giá trị cũ không
            if (!updatedValue.equals(originalValue)) {
              try {
                Integer lotID = (Integer) modelTableLot.getValueAt(row, 12);

                // Kiểm tra nhập liệu
                if (column == 2 && updatedValue.isEmpty()) {
                  DialogHelper.alert(null, "Vui lòng nhập Mã Lô trong bảng!");
                  return;
                }

                if ((column == 7)) {
                  try {
                    new BigDecimal(updatedValue); // Kiểm tra giá trị là số hợp lệ
                  } catch (NumberFormatException ex) {
                    DialogHelper.alert(null, "Khối lượng phải là một số hợp lệ!");
                    return;
                  }
                }

                if (column == 11 && updatedValue.isEmpty()) {
                  DialogHelper.alert(null, "Vui lòng nhập Số Pallet trong bảng!");
                  return;
                }


                // Cập nhật dữ liệu


                Lot updatedLot = new Lot();

            // Gán các thuộc tính từng bước
                updatedLot.setLotID(lotID);
                updatedLot.setLotIDU(modelTableLot.getValueAt(row, 2).toString());
                updatedLot.setProduct(lots.get(row).getProduct());
                updatedLot.setProductionTime(lots.get(row).getProductionTime());
                updatedLot.setExpirationDate(lots.get(row).getExpirationDate());
                updatedLot.setWeight(
                        new BigDecimal(modelTableLot.getValueAt(row, 6).toString())
                                .subtract(new BigDecimal(modelTableLot.getValueAt(row, 7).toString()))
                );
                updatedLot.setWarehouseWeight(new BigDecimal(modelTableLot.getValueAt(row, 6).toString()));
                updatedLot.setWeightDeviation(new BigDecimal(modelTableLot.getValueAt(row, 7).toString()));
                updatedLot.setShift(lots.get(row).getShift());
                updatedLot.setProductionGroup(lots.get(row).getProductionGroup());
                updatedLot.setWarehouseStaff(lots.get(row).getWarehouseStaff());

                Pallet palletUpdate = daoPallet.selectByLotID(lotID);
                palletUpdate.setPalletIDU(modelTableLot.getValueAt(row, 11).toString());


                daoLot.update(updatedLot); // Gọi phương thức update từ DAO
                daoPallet.update(palletUpdate);
                // Cập nhật giá trị ban đầu
                originalData[row][column] = updatedValue;
                DialogHelper.alert(null, "Dữ liệu đã được cập nhật.");
                updateTableLot(); // Cập nhật lại bảng
              } catch (Exception ex) {
                if (ex.getMessage().contains("UNIQUE")) {
                  DialogHelper.alert(null, "Mã Lô đã tồn tại! Vui lòng nhập mã khác.");
                } else {
                  DialogHelper.alert(null, "Lỗi khi cập nhật Lô: " + ex.getMessage());
                }
              }
            }
          }
        }
      }
    });


    // Thêm sự kiện lắng nghe khi người dùng chọn dòng
    tableLot.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting() && tableLot.getSelectedRow() != -1) {


        int selectedRow = tableLot.getSelectedRow();
        Integer LotID = (Integer) modelTableLot.getValueAt(selectedRow, 12);

        // Lấy sản phẩm từ cơ sở dữ liệu
        Lot selectedLot = daoLot.selectbyID(LotID);

        if (selectedLot != null) {
          button4.setEnabled(true);

          // Gán giá trị cho các trường combo box và text field
          getMaHangComboBox().setSelectedItem(selectedLot.getProduct().getProductName());

          getSoLoField().setText(selectedLot.getLotIDU());
          getKlBiField().setText(selectedLot.getWeightDeviation().toString());

          getToSXComboBox().setSelectedItem(selectedLot.getProductionGroup().getGroupName());
          getCaSanxuatComboBox().setSelectedItem(selectedLot.getShift().getShiftName());
          getThukhoComboBox().setSelectedItem(selectedLot.getWarehouseStaff().getStaffName());
          getSoPalletTe().setText(daoPallet.selectByLotID(selectedLot.getLotID()).getPalletIDU());

          // Chuyển đổi LocalDate sang java.util.Date
          LocalDate expirationDate = selectedLot.getProductionTime();
          Date utilDate = Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

// Gán giá trị vào JDateChooser
          getNSXDate().setDate(utilDate);

          getKLCLabel().setText(selectedLot.getWarehouseWeight().toString());
          getKLTField().setText(selectedLot.getWeight().toString());
        }
      }
    });

  }




  //getter

  // Getter cho maHangComboBox
  public JComboBox<String> getMaHangComboBox() {
    return maHangComboBox;
  }

  // Getter cho mauField
  public JTextField getMauField() {
    return mauField;
  }

  // Getter cho hanSDField
  public JTextField getHanSDField() {
    return hanSDField;
  }

  // Getter cho soLoField
  public JTextField getSoLoField() {
    return soLoField;
  }

  // Getter cho klBiField
  public JTextField getKlBiField() {
    return klBiField;
  }

  // Getter cho printerBox
  public JComboBox<String> getPrinterBox() {
    return printerBox;
  }

  //center panel

  public JComboBox<String> getToSXComboBox() {
    return ToSXComboBox;
  }

  public JComboBox<String> getCaSanxuatComboBox() {
    return caSanxuatComboBox;
  }

  public JComboBox<String> getThukhoComboBox() {
    return ThukhoComboBox;
  }

  public JTextField getSoPalletTe() {
    return SoPalletTe;
  }

  public JDateChooser getNSXDate() {
    return NSXDate;
  }

  //right center

  public JLabel getKLCLabel() {
    return KLCLabel;
  }

  public JTextField getKLTField() {
    return KLTField;
  }


  public void  RefreshT() {
      getSoLoField().setText("");
      getKlBiField().setText("0");
    getSoPalletTe().setText("");
    getNSXDate().setDate(new Date());
    getKLCLabel().setText("100.00");
  }



}
