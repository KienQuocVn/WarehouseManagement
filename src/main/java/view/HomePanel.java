package view;

import Utils.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class HomePanel extends JPanel {

  public HomePanel() {
    setLayout(new BorderLayout());

    // Tạo container phía trên
    add(createSoftwarePanel(), BorderLayout.NORTH);

    // Tạo phần header panel với các phần (Left, Center, Right)
    add(createHeaderPanel(), BorderLayout.CENTER);

    // Thêm bảng JTable
    add(createTablePanel(), BorderLayout.SOUTH);
  }

  // Phương thức tạo panel tiêu đề phần mềm
  private JPanel createSoftwarePanel() {
    JPanel softwarePanel = new JPanel();
    softwarePanel.setBackground(Color.WHITE);
    softwarePanel.setBorder(new RoundedBorder(0));

    JLabel softwareLabel = new JLabel("PHẦN MỀM CÂN XUẤT NHẬP KHO");
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
    headerPanel.setBorder(new EmptyBorder(6, 4, 6, 4));

    headerPanel.add(createLeftPanel());
    headerPanel.add(createCenterPanel());
    headerPanel.add(createRightPanel());

    return headerPanel;
  }

  // Panel bên trái
  private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridBagLayout());
    leftPanel.setBorder(new RoundedBorder(20));
    leftPanel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;

    // Tiêu đề (căn giữa)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // Tiêu đề chiếm 2 cột
    JLabel titleLabel = new JLabel("THÔNG TIN ĐƠN HÀNG", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
    leftPanel.add(titleLabel, gbc);

    // Mã Hàng
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1; // Reset về 1 cột
    gbc.anchor = GridBagConstraints.WEST; // Căn trái
    JLabel maHangLabel = new JLabel("Mã Hàng:");
    leftPanel.add(maHangLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> maHangComboBox = new JComboBox<>(new String[]{"Đơn 1", "Đơn 2", "Đơn 3"});
    maHangComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    leftPanel.add(maHangComboBox, gbc);

    // Màu
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel mauLabel = new JLabel("Màu:");
    leftPanel.add(mauLabel, gbc);

    gbc.gridx = 1;
    JTextField mauField = new JTextField();
    mauField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    mauField.setBackground(new Color(255, 255, 204)); // Màu vàng nhạt
    leftPanel.add(mauField, gbc);

    // Hạn SD
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel hanSDLabel = new JLabel("Hạn SD (Ngày):");
    leftPanel.add(hanSDLabel, gbc);

    gbc.gridx = 1;
    JTextField hanSDField = new JTextField("0");
    hanSDField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    leftPanel.add(hanSDField, gbc);

    // Số Lô
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel soLoLabel = new JLabel("Số Lô:");
    leftPanel.add(soLoLabel, gbc);

    gbc.gridx = 1;
    JTextField soLoField = new JTextField();
    soLoField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    leftPanel.add(soLoField, gbc);

    // KL Bi
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel klBiLabel = new JLabel("KL BÌ (Kg):");
    leftPanel.add(klBiLabel, gbc);

    gbc.gridx = 1;
    JTextField klBiField = new JTextField("0");
    klBiField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    leftPanel.add(klBiField, gbc);

    // Printer
    gbc.gridx = 0;
    gbc.gridy = 6;
    JLabel printerLabel = new JLabel("Printer:");
    leftPanel.add(printerLabel, gbc);

    gbc.gridx = 1;
    JPanel printerPanel = new JPanel(new BorderLayout());
    JComboBox<String> printerBox = new JComboBox<>(new String[]{"Foxit PhantomPDF Printer"});
    JButton printButton = new JButton();
    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon = new javax.swing.ImageIcon(getClass().getResource("/img/printer.png"));
    Image iconImage = originalIcon.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage = iconImage.getScaledInstance(23, 23, Image.SCALE_SMOOTH);
    printButton.setBackground(new Color(71,138,173,255));
    printButton.setIcon(new ImageIcon(scaledImage));
    printButton.setForeground(Color.WHITE);
    printerPanel.add(printerBox, BorderLayout.CENTER);
    printerPanel.add(printButton, BorderLayout.EAST);
    leftPanel.add(printerPanel, gbc);

    return leftPanel;
  }




  // Panel trung tâm
  private JPanel createCenterPanel() {
    JPanel CentePanel = new JPanel();
    CentePanel.setLayout(new GridBagLayout());
    CentePanel.setBorder(new RoundedBorder(20));
    CentePanel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;

    // Tiêu đề (căn giữa)
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // Tiêu đề chiếm 2 cột
    JLabel titleLabel = new JLabel("THÔNG TIN SẢN XUẤT", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
    CentePanel.add(titleLabel, gbc);

    // Mã Hàng
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1; // Reset về 1 cột
    gbc.anchor = GridBagConstraints.WEST; // Căn trái
    JLabel maHangLabel = new JLabel("Tổ Sản Xuất:");
    CentePanel.add(maHangLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> maHangComboBox = new JComboBox<>(new String[]{"Tổ 1", "Tổ 2", "Tổ 3"});
    maHangComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    CentePanel.add(maHangComboBox, gbc);

    // Màu
    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel mauLabel = new JLabel("Ca Sản Xuất:");
    CentePanel.add(mauLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> caSanxuatComboBox = new JComboBox<>(new String[]{"Ca 1", "CA 2", "CA 3"});
    caSanxuatComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    CentePanel.add(caSanxuatComboBox, gbc);


    // Số Lô
    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel soLoLabel = new JLabel("Thủ Kho:");
    CentePanel.add(soLoLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> ThukhoComboBox = new JComboBox<>(new String[]{"Trung", "Quoc"});
    ThukhoComboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    CentePanel.add(ThukhoComboBox, gbc);

    // KL Bi
    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel klBiLabel = new JLabel("Số Pallet:");
    CentePanel.add(klBiLabel, gbc);

    gbc.gridx = 1;
    JTextField klBiField = new JTextField("0");
    klBiField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
    CentePanel.add(klBiField, gbc);

    // Printer
    gbc.gridx = 0;
    gbc.gridy = 5;
    JLabel printerLabel = new JLabel("Tìm mã hàng và số lô:");
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
    Image scaledImage = iconImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    timButton.setIcon(new ImageIcon(scaledImage));
    timButton.setBackground(new Color(71,138,173,255));

    JButton lamMoiButton = new JButton();
    // Tải icon từ file và thay đổi kích thước
    ImageIcon originalIcon2 = new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"));
    Image iconImage2 = originalIcon2.getImage(); // Lấy hình ảnh từ ImageIcon

// Thay đổi kích thước của icon (ví dụ: 20x20 pixels)
    Image scaledImage2 = iconImage2.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
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
    JLabel DonviLabel2 = new JLabel("", SwingConstants.LEFT);
    DonviLabel2.setFont(new Font("Arial", Font.BOLD, 30));
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
    JLabel SCLabel2 = new JLabel("0.00", SwingConstants.CENTER);
    SCLabel2.setFont(new Font("Arial", Font.BOLD, 50));
    RPane2.add(SCLabel2, BorderLayout.CENTER);

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
    JLabel KLTLabel = new JLabel("Khối Lượng Tịnh (KG):", SwingConstants.LEFT);
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
    JTextField KLTField = new JTextField();
    KLTField.setBackground(new Color(255, 255, 204)); // Màu vàng nhạt
    KLTField.setText("0.00");
    KLTField.setFont(new Font("Arial", Font.BOLD, 14));
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
  private JScrollPane createTablePanel() {
    String[] columns = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân", "KL Bì", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet"};
    Object[][] data = {
            {17, "145R12C CA406T 86/84P CASUMINA TL", "BD2024100", "Tổ 1", "Ca 1", "08/10/2024", 100.00, 13.00, 85.00, "Dương Nguyễn Tấn Hòa", 12.00, "BL100"},
            {16, "145R12C CA406T 86/84P CASUMINA TL", "BD2024015", "Tổ 1", "Ca 1", "08/10/2024", 85.00, 11.75, 70.00, "Dương Nguyễn Tấn Hòa", 18.00, "BL015"},
    };

    DefaultTableModel model = new DefaultTableModel(data, columns);
    JTable table = new JTable(model);
    styleTable(table);

    return new JScrollPane(table);
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

}
