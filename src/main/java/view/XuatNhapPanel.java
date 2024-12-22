package view;

import Utils.RoundedBorder;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class XuatNhapPanel extends JPanel {
  public XuatNhapPanel() {

    // Thiết lập layout cho giao diện chính
    setLayout(new BorderLayout());

    // ======= Tạo panel tiêu đề (trên cùng) =======
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(Color.WHITE); // Đặt màu nền cho tiêu đề
    JLabel titleLabel = new JLabel("XUẤT / NHẬP KHO");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(Color.BLACK); // Đặt màu chữ
    titlePanel.add(titleLabel);

    // ======= Tạo panel nội dung (bên dưới) =======
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(Color.WHITE); // Đặt màu nền cho nội dung

// ======= Tạo JSplitPane để chia theo chiều ngang (left-right) =======
    JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPaneHorizontal.setResizeWeight(0.5); // Tỉ lệ chia đều giữa hai cột

// ======= Cột bên trái =======
    JPanel leftPanel = new JPanel(new BorderLayout());
    JPanel wrapperPanelLeft = new JPanel(new BorderLayout());
    JPanel mainPanelLeft = new JPanel(new BorderLayout());
    JPanel topPanelLeft = new JPanel(new BorderLayout());

// Tạo JPanel để chứa JLabel và JButton
    JPanel labelPanelLeft = new JPanel(new BorderLayout()); // Dùng BorderLayout để căn đối xứng

// Tạo JLabel "Mã Hàng"
    JLabel label = new JLabel("Mã Hàng:");
    label.setFont(new Font("Arial", Font.BOLD, 15)); // Cài đặt font cho JLabel
    labelPanelLeft.add(label, BorderLayout.WEST); // Đặt JLabel vào vị trí WEST (bên trái)

// Tạo JButton "Chọn Hết"
    JButton btnChonHet = new JButton("Chọn Hết");
    btnChonHet.setBackground(new Color(70, 130, 180));
    labelPanelLeft.add(btnChonHet, BorderLayout.EAST); // Đặt JButton vào vị trí EAST (bên phải)

// Thêm labelPanel vào topPanel
    topPanelLeft.add(labelPanelLeft, BorderLayout.NORTH);

    String[] columnNames = {"Mã Hàng", "Số Lô", "Xuất"};
    Object[][] data = {
        {"MH01", "L01", true},
        {"MH02", "L02", false},
        {"MH03", "L03", true}
    };

// Tạo DefaultTableModel với dữ liệu mới
    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
      @Override
      public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2) {
          return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
      }
    };

    JTable table = new JTable(tableModel);
    table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox()));

    table.getTableHeader().setBackground(new Color(255, 153, 51)); // Màu nền tiêu đề

    JScrollPane tableScrollPane = new JScrollPane(table);
    tableScrollPane.setPreferredSize(new Dimension(550, 200));
    topPanelLeft.add(tableScrollPane, BorderLayout.CENTER);

    JPanel bottomPanelLeft = new JPanel(new BorderLayout());
    JPanel labelPanelBottomLeft = new JPanel(new BorderLayout());

    JPanel topRowPanelLeft = new JPanel(new BorderLayout());
    JLabel labelBottomLeft = new JLabel("Phiếu Xuất:");
    labelBottomLeft.setFont(new Font("Arial", Font.BOLD, 15));
    topRowPanelLeft.add(labelBottomLeft, BorderLayout.WEST);

    JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton btnExport = new JButton("Xuất Báo Cáo");
    btnExport.setBackground(new Color(70, 130, 180));
    JButton btnSave = new JButton("Lưu Tạm");
    btnSave.setBackground(new Color(70, 130, 180));
    buttonPanelLeft.add(btnExport);
    buttonPanelLeft.add(btnSave);

    topRowPanelLeft.add(buttonPanelLeft, BorderLayout.EAST);
    labelPanelBottomLeft.add(topRowPanelLeft, BorderLayout.NORTH);

    // Tạo JDateChooser cho Từ Ngày và Đến Ngày
    JDateChooser fromDateChooser = new JDateChooser();
    fromDateChooser.setDate(new Date());
    fromDateChooser.setDateFormatString("dd/MM/yyyy");

// Thay đổi kích thước JTextField bên trong JDateChooser
    JTextField fromDateTextField = (JTextField) fromDateChooser.getDateEditor().getUiComponent();
    fromDateTextField.setPreferredSize(new Dimension(200, 25));  // Kích thước mới cho JTextField

// Cũng thay đổi kích thước của JDateChooser nếu cần
    fromDateChooser.setPreferredSize(new Dimension(200, 25));  // Kích thước của JDateChooser

    JDateChooser toDateChooser = new JDateChooser();
    toDateChooser.setDate(new Date());
    toDateChooser.setDateFormatString("dd/MM/yyyy");

// Thay đổi kích thước JTextField bên trong JDateChooser
    JTextField toDateTextField = (JTextField) toDateChooser.getDateEditor().getUiComponent();
    toDateTextField.setPreferredSize(new Dimension(200, 25));  // Kích thước mới cho JTextField

// Cũng thay đổi kích thước của JDateChooser nếu cần
    toDateChooser.setPreferredSize(new Dimension(200, 25));  // Kích thước của JDateChooser

// Tạo phần lọc dữ liệu (hàng thứ hai)
    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Dùng FlowLayout để căn các thành phần bên trái
    filterPanel.add(new JLabel("Từ:"));
    filterPanel.add(fromDateChooser);

    filterPanel.add(new JLabel("Đến:"));
    filterPanel.add(toDateChooser);


// Thêm các thành phần lọc vào bottomPanel
    JButton btnFilter = new JButton(resizeIcon("/img/search.png", 15, 15));
    btnFilter.setPreferredSize(new Dimension(75, 25));
    filterPanel.add(btnFilter);

// Thêm filterPanel vào labelPanelBottom ở vị trí CENTER
    labelPanelBottomLeft.add(filterPanel, BorderLayout.CENTER);


    String[] leftColumnNames = {"SỐ PHIẾU", "KHÁCH HÀNG", "NGÀY XUẤT","THỦ KHO"};
    DefaultTableModel leftTableModel = new DefaultTableModel(leftColumnNames, 0);
    JTable leftTable = new JTable(leftTableModel);
    leftTable.getTableHeader().setBackground(new Color(255, 153, 51));

    JScrollPane leftScrollPane = new JScrollPane(leftTable);
    leftScrollPane.setPreferredSize(new Dimension(550, 305));

    bottomPanelLeft.add(labelPanelBottomLeft, BorderLayout.NORTH);
    bottomPanelLeft.add(leftScrollPane, BorderLayout.CENTER);

    mainPanelLeft.add(topPanelLeft, BorderLayout.NORTH);
    mainPanelLeft.add(bottomPanelLeft, BorderLayout.CENTER);

    JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanelLeft, bottomPanelLeft);
    splitPaneLeft.setResizeWeight(0.5);

    wrapperPanelLeft.add(splitPaneLeft, BorderLayout.CENTER);
    leftPanel.add(wrapperPanelLeft, BorderLayout.CENTER);

// ======= Cột bên phải =======
    JPanel rightPanel = new JPanel(new BorderLayout());

// Tạo JSplitPane để chia theo chiều dọc (top-bottom)
    JSplitPane centerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

// Thông tin phiếu
    JPanel inputPanel = new JPanel(new GridBagLayout());
    inputPanel.setBorder(BorderFactory.createEmptyBorder(-20,0, -25,0));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 0, 0, 0); // Điều chỉnh khoảng cách giữa các thành phần

// Cấu hình cho JLabel và JTextField
    JLabel labelSoPhieu = new JLabel("Số Phiếu:");
    labelSoPhieu.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.1;  // JLabel chiếm 3 phần
    inputPanel.add(labelSoPhieu, gbc);

    JTextField txtSoPhieu = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0.9;  // JTextField chiếm 7 phần
    txtSoPhieu.setPreferredSize(new Dimension(200, 30));  // Tăng chiều cao JTextField
    inputPanel.add(txtSoPhieu, gbc);

    JLabel labelKhachHang = new JLabel("Khách Hàng:");
    labelKhachHang.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.1;
    inputPanel.add(labelKhachHang, gbc);

    JTextField txtKhachHang = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 0.9;
    txtKhachHang.setPreferredSize(new Dimension(200, 30));  // Tăng chiều cao JTextField
    inputPanel.add(txtKhachHang, gbc);

    JLabel labelMaHang = new JLabel("Mã Hàng:");
    labelMaHang.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0.1;
    inputPanel.add(labelMaHang, gbc);

    JTextField txtMaHang = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.weightx = 0.9;
    txtMaHang.setPreferredSize(new Dimension(200, 30));  // Tăng chiều cao JTextField
    inputPanel.add(txtMaHang, gbc);

    JLabel labelSoLo = new JLabel("Số Lô:");
    labelSoLo.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.weightx = 0.1;
    inputPanel.add(labelSoLo, gbc);

    JTextField txtSoLo = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.weightx = 0.9;
    txtSoLo.setPreferredSize(new Dimension(200, 30));  // Tăng chiều cao JTextField
    inputPanel.add(txtSoLo, gbc);

    JLabel labelThuKho = new JLabel("Thủ Kho:");
    labelThuKho.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.weightx = 0.1;
    inputPanel.add(labelThuKho, gbc);

    JTextField txtThuKho = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.weightx = 0.9;
    txtThuKho.setPreferredSize(new Dimension(200, 30));  // Tăng chiều cao JTextField
    inputPanel.add(txtThuKho, gbc);


    // Tạo một JPanel cho các nút
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 40, 10));  // 1 hàng, 4 cột, khoảng cách ngang giữa các nút là 10px
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,30, 0,30));
// Tạo các nút
    JButton btnXemPhieu = new JButton("Xem Phiếu");
    btnXemPhieu.setBackground(new Color(70, 130, 180));
    JButton btnPhieuMoi = new JButton("Phiếu Mới");
    btnPhieuMoi.setBackground(new Color(70, 130, 180));
    JButton btnLuu = new JButton("Lưu");
    btnLuu.setBackground(new Color(70, 130, 180));
    JButton btnQuayLai = new JButton("Quay Lại");
    btnQuayLai.setBackground(new Color(70, 130, 180));

// Thêm các nút vào buttonPanel
    buttonPanel.add(btnXemPhieu);
    buttonPanel.add(btnPhieuMoi);
    buttonPanel.add(btnLuu);
    buttonPanel.add(btnQuayLai);

// Thêm buttonPanel vào inputPanel
    gbc.gridx = 0;
    gbc.gridy = 5;  // Đặt vị trí của buttonPanel ở dưới cùng
    gbc.gridwidth = 2;  // Nút chiếm toàn bộ chiều rộng
    inputPanel.add(buttonPanel, gbc);

// Đặt chiều cao cho inputPanel
    inputPanel.setPreferredSize(new Dimension(900, 200)); // Chiều cao bảng bên dưới


// Tạo bảng bên dưới (Thông Tin Xuất Kho)
    String[] rightColumnNames = {"Số Phiếu", "Khách Hàng", "Mã Hàng", "Số Lô", "KL SP", "KL Bì", "KL Tổng", "Tổ", "Ca"};
    DefaultTableModel rightTableModel = new DefaultTableModel(rightColumnNames, 0);
    JTable rightTable = new JTable(rightTableModel);
    rightTable.getTableHeader().setBackground(new Color(255, 153, 51));
    JScrollPane rightScrollPane = new JScrollPane(rightTable);
    rightScrollPane.setPreferredSize(new Dimension(900, 405));

// Đặt các bảng vào JSplitPane
    centerPanel.setTopComponent(inputPanel);
    centerPanel.setBottomComponent(rightScrollPane);

// Thêm centerPanel vào giao diện chính
    rightPanel.add(centerPanel, BorderLayout.CENTER);

// ======= Thêm hai cột vào JSplitPane =======
    splitPaneHorizontal.setLeftComponent(leftPanel);
    splitPaneHorizontal.setRightComponent(rightPanel);


// ======= Thêm vào giao diện chính =======
    add(titlePanel, BorderLayout.NORTH);
    add(splitPaneHorizontal, BorderLayout.CENTER);
  }

  private ImageIcon resizeIcon(String imagePath, int width, int height) {
    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
    Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImage);
  }

}
