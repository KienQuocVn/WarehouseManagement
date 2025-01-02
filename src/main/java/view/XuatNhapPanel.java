package view;

import com.toedter.calendar.JDateChooser;
import dao.DaoLot;
import dao.DaoProduct;
import dao.DaoProductionGroup;
import dao.DaoShift;
import dao.DaoTransactionDetail;
import dao.DaoWarehouseStaff;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Lot;
import model.Product;
import model.TransactionDetail;

public class XuatNhapPanel extends JPanel {
  private JTextField txtMaHang;
  private JTextField txtSoLo;
  private JTextField txtThuKho;

  private JTable table1,table2,table3;
  private DefaultTableModel tableModel1,tableModel2,tableModel3;
  private DaoLot daoLot;
  private DaoShift daoShift;
  private DaoProductionGroup daoProductionGroup;
  private DaoProduct daoProduct;
  private DaoWarehouseStaff daoWarehouseStaff;
  private DaoTransactionDetail daoTransactionDetail;
  private boolean isNewMode = false;
  DefaultTableModel rightTableModel = new DefaultTableModel();


  public XuatNhapPanel() {
    daoLot = new DaoLot();
    daoProductionGroup = new DaoProductionGroup();
    daoShift = new DaoShift();
    daoProduct = new DaoProduct();
    daoWarehouseStaff = new DaoWarehouseStaff();
    daoTransactionDetail = new DaoTransactionDetail();

    txtMaHang = new JTextField();
    txtSoLo = new JTextField();
    txtThuKho = new JTextField();

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

    JPanel labelPanelLeft = new JPanel(new BorderLayout());
    labelPanelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    JLabel label = new JLabel("Mã Hàng:");
    label.setFont(new Font("Arial", Font.BOLD, 15));
    labelPanelLeft.add(label, BorderLayout.WEST);

    JButton btnChonHet = new JButton("Chọn Hết");
    btnChonHet.setBackground(new Color(70, 130, 180));
    btnChonHet.addActionListener(e -> {
      // Lặp qua tất cả các hàng trong tableModel2
      for (int i = 0; i < tableModel2.getRowCount(); i++) {
        tableModel2.setValueAt(true, i, 1); // Đặt giá trị cột checkbox (cột 1) thành true
      }
    });

    labelPanelLeft.add(btnChonHet, BorderLayout.EAST);

    topPanelLeft.add(labelPanelLeft, BorderLayout.NORTH);



    // Dữ liệu cho bảng 1
    String[] columnNames1 = {"Mã Hàng"};
    tableModel1 = new DefaultTableModel(columnNames1, 0);
    table1 = new JTable(tableModel1);
    JScrollPane tableScrollPane1 = new JScrollPane(table1);

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    for (int i = 0; i < table1.getColumnCount(); i++) {
      table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    table1.setRowHeight(25);
    table1.getTableHeader().setBackground(new Color(255, 153, 51));
    table1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
    table1.setFont(new Font("Arial", Font.PLAIN, 12));
    // Lắng nghe sự kiện chọn dòng trong table1
    table1.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) { // Đảm bảo chỉ xử lý khi người dùng thực sự chọn
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
          // Lấy ProductName từ dòng được chọn
          String selectedProductName = (String) tableModel1.getValueAt(selectedRow, 0);

          // Lấy ProductID dựa trên ProductName
          Product selectedProduct = daoProduct.selectbyName(selectedProductName);
          if (selectedProduct != null) {
            // Lấy danh sách LotIDU từ DaoLot
            List<String> lotIDs = daoLot.getLotIDUByProductID(selectedProduct.getProductID());

            // Load LotIDU vào table2
            loadDataToTable2(lotIDs);
          }
        }
      }
    });
    table1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
          if (isNewMode) {
            loadDataToFields(selectedRow);
          }
        }
      }
    });

    tableScrollPane1.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(0, 5, 0, 0),
        BorderFactory.createMatteBorder(0, 0, 0, 10, Color.GRAY)
    ));

    tableScrollPane1.setPreferredSize(new Dimension(300, 200));


    // Dữ liệu cho bảng 2
    String[] columnNames2 = {"Số Lô", "Xuất"};
    tableModel2 = new DefaultTableModel(columnNames2, 0) {
      @Override
      public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) {
          return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
      }
    };

    // Tạo bảng 2
    table2 = new JTable(tableModel2);
    table2.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()));
    table2.getTableHeader().setBackground(new Color(255, 153, 51));
    JScrollPane tableScrollPane2 = new JScrollPane(table2);
    tableScrollPane2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
    tableScrollPane2.setPreferredSize(new Dimension(300, 200));

    // Tạo JPanel để chứa hai bảng
    JPanel tablePanel = new JPanel(new BorderLayout());

    // Thêm cả hai bảng vào tablePanel
    tablePanel.add(tableScrollPane1, BorderLayout.WEST);
    tablePanel.add(tableScrollPane2, BorderLayout.EAST);

    // Thêm tablePanel vào topPanelLeft
    topPanelLeft.add(tablePanel, BorderLayout.CENTER);
    //======================================================================================


    JPanel bottomPanelLeft = new JPanel(new BorderLayout());
    JPanel labelPanelBottomLeft = new JPanel(new BorderLayout());

    JPanel topRowPanelLeft = new JPanel(new BorderLayout());
    JLabel labelBottomLeft = new JLabel("Phiếu Xuất:");
    labelBottomLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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
    btnFilter.setBackground(new Color(70, 130, 180));
    btnFilter.setPreferredSize(new Dimension(95, 25));
    filterPanel.add(btnFilter);

    // Thêm filterPanel vào labelPanelBottom ở vị trí CENTER
    labelPanelBottomLeft.add(filterPanel, BorderLayout.CENTER);


    String[] leftColumnNames3 = {"Số Phiếu", "Khách Hàng", "Ngày Xuất","Thủ Kho"};
    tableModel3 = new DefaultTableModel(leftColumnNames3, 0);
    table3 = new JTable(tableModel3);
    table3.getTableHeader().setBackground(new Color(255, 153, 51));
    JScrollPane leftScrollPane = new JScrollPane(table3);
    leftScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

    DefaultTableCellRenderer centerRenderer3 = new DefaultTableCellRenderer();
    for (int i = 0; i < table3.getColumnCount(); i++) {
      table3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer3);
    }

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
    JSplitPane centerPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    // Thông tin phiếu
    JPanel inputPanel = new JPanel(new GridBagLayout());
    inputPanel.setBorder(BorderFactory.createEmptyBorder(-20,0, -25,0));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 10, 3, 0); // Điều chỉnh khoảng cách giữa các thành phần

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
    txtSoPhieu.setPreferredSize(new Dimension(200, 27));
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
    txtKhachHang.setPreferredSize(new Dimension(200, 27));
    inputPanel.add(txtKhachHang, gbc);

    JLabel labelMaHang = new JLabel("Mã Hàng:");
    labelMaHang.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0.1;
    inputPanel.add(labelMaHang, gbc);

    txtMaHang = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.weightx = 0.9;
    txtMaHang.setPreferredSize(new Dimension(200, 27));
    inputPanel.add(txtMaHang, gbc);

    JLabel labelSoLo = new JLabel("Số Lô:");
    labelSoLo.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.weightx = 0.1;
    inputPanel.add(labelSoLo, gbc);

    txtSoLo = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.weightx = 0.9;
    txtSoLo.setPreferredSize(new Dimension(200, 27));
    inputPanel.add(txtSoLo, gbc);

    JLabel labelThuKho = new JLabel("Thủ Kho:");
    labelThuKho.setFont(new Font("Arial", Font.BOLD, 15));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.weightx = 0.1;
    inputPanel.add(labelThuKho, gbc);

    txtThuKho = new JTextField();
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.weightx = 0.9;
    txtThuKho.setPreferredSize(new Dimension(200, 27));
    inputPanel.add(txtThuKho, gbc);


    // Tạo một JPanel cho các nút
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 40, 10));  // 1 hàng, 4 cột, khoảng cách ngang giữa các nút là 10px
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,30, 0,30));
    // Tạo các nút
    JButton btnXemPhieu = new JButton("Xem Phiếu");
    btnXemPhieu.setBackground(new Color(70, 130, 180));

    JButton btnLuuTam = new JButton("Lưu tạm");
    JButton btnPhieuMoi = new JButton("Phiếu Mới");
    btnLuuTam.setBackground(new Color(195, 195, 195));
    btnLuuTam.setForeground(Color.BLACK);
    btnLuuTam.setEnabled(false);
    btnLuuTam.addActionListener(e -> {
      // Lấy dữ liệu từ các textfield
      String soPhieu = txtSoPhieu.getText();
      String khachHang = txtKhachHang.getText();
      String maHang = txtMaHang.getText();
      String soLo = txtSoLo.getText();
      String thuKho = txtThuKho.getText();

      // Kiểm tra xem các trường có dữ liệu không
      if (soPhieu.isEmpty() || khachHang.isEmpty() || maHang.isEmpty() || soLo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        return;
      }

      // Tách txtSoLo thành danh sách LotID
      String[] lotIDs = soLo.split(",\\s*");


      // Thêm từng LotID vào bảng tạm, đồng thời đếm tăng LotID
      for (String lotID : lotIDs) {
        rightTableModel.addRow(new Object[] {soPhieu, khachHang, maHang, lotID, "", "", "", "", "", ""});
      }

      // Delete tm table 1
      int selectedRow = table1.getSelectedRow();
      if (selectedRow != -1) {
        tableModel1.removeRow(selectedRow);
      }

      // Xóa tạm dữ liệu từ bảng 2 (hiển thị)
      for (String lotID : lotIDs) {
        for (int i = 0; i < tableModel2.getRowCount(); i++) {
          String tableLotID = (String) tableModel2.getValueAt(i, 0);
          if (tableLotID.equals(lotID)) {
            tableModel2.removeRow(i);
            i--; // Điều chỉnh chỉ số để tránh bỏ qua hàng tiếp theo
          }
        }
      }
      // Reset các trường nhập liệu
      txtSoPhieu.setText(""); // Cập nhật lại `LotID` mới nhất
      txtKhachHang.setText("");
      txtMaHang.setText("");
      txtSoLo.setText("");
      txtThuKho.setText("");

      // Đặt lại trạng thái nút
      btnLuuTam.setEnabled(false);
      btnLuuTam.setBackground(new Color(195, 195, 195));
      btnPhieuMoi.setEnabled(true);
      btnPhieuMoi.setBackground(new Color(70, 130, 180));

      JOptionPane.showMessageDialog(this, "Dữ liệu đã được lưu tạm thành công và bảng 2 đã được cập nhật!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    });




    btnPhieuMoi.setBackground(new Color(70, 130, 180));
    btnPhieuMoi.addActionListener(e -> {
      btnLuuTam.setEnabled(true);
      btnLuuTam.setBackground(new Color(70, 130, 180));
      btnPhieuMoi.setEnabled(false);
      btnPhieuMoi.setBackground(new Color(195, 195, 195));
      btnPhieuMoi.setForeground(Color.BLACK);

      // Bật chế độ "Phiếu mới"
      isNewMode = true;

      // Reset các trường nhập liệu
      txtSoPhieu.setText("");
      txtKhachHang.setText("");
      txtMaHang.setText("");
      txtSoLo.setText("");
      txtThuKho.setText("");

    });


    JButton btnQuayLai = new JButton("Quay Lại");
    btnQuayLai.setBackground(new Color(70, 130, 180));

    // Thêm các nút vào buttonPanel
    buttonPanel.add(btnXemPhieu);
    buttonPanel.add(btnPhieuMoi);
    buttonPanel.add(btnLuuTam);
    buttonPanel.add(btnQuayLai);

    // Thêm buttonPanel vào inputPanel
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    inputPanel.add(buttonPanel, gbc);

    // Đặt chiều cao cho inputPanel
    inputPanel.setPreferredSize(new Dimension(900, 200)); // Chiều cao bảng bên dưới


    // Tạo bảng bên dưới (Thông Tin Xuất Kho)
    String[] rightColumnNames = {"Số Phiếu", "Khách Hàng", "Mã Hàng", "Số Lô", "KL SP", "KL Bì", "KL Tổng", "Tổ", "Ca","Ngày Xuất"};
    rightTableModel = new DefaultTableModel(rightColumnNames, 0);
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

    loadDataToTable1();
    loadDataToTable3();
  }


  private void loadDataToTable1() {
    tableModel1.setRowCount(0);

    List<Product> products = daoProduct.selectAll();

    for (Product product : products) {
      Object[] row = {
          product.getProductName()
      };

      tableModel1.addRow(row);
    }
  }

  private void loadDataToTable2(List<String> lotIDs) {
    tableModel2.setRowCount(0);

    for (String lotID : lotIDs) {
      Object[] row = {lotID, false};
      tableModel2.addRow(row);
    }
  }

  private void loadDataToFields(int selectedRow) {
    String soPhieu = tableModel1.getValueAt(selectedRow, 0) != null
        ? tableModel1.getValueAt(selectedRow, 0).toString()
        : "";
    txtMaHang.setText(soPhieu);

    // Lấy ProductName từ bảng
    String selectedProductName = (String) tableModel1.getValueAt(selectedRow, 0);
    // Lấy ProductID từ tên sản phẩm
    Product selectedProduct = daoProduct.selectbyName(selectedProductName);
    if (selectedProduct != null) {
      // Lấy danh sách LotIDU dựa trên ProductID
      List<String> lotIDs = daoLot.getLotIDUByProductID(selectedProduct.getProductID());
      String lotIDsString = String.join(", ", lotIDs);
      txtSoLo.setText(lotIDsString);

      String staffName = daoProduct.getStaffNameByProductName(selectedProductName);
      txtThuKho.setText(staffName != null ? staffName : "");
    }
  }


  private void loadDataToTable3() {
    // Xóa tất cả dữ liệu cũ trong bảng
    tableModel3.setRowCount(0);

    // Lấy dữ liệu từ DAO
    List<Object[]> transactionDetails = daoTransactionDetail.selectTransactionDetails();

    // Thêm dữ liệu vào bảng
    for (Object[] transactionDetail : transactionDetails) {
      // Thêm dòng vào bảng
      tableModel3.addRow(transactionDetail);
    }
  }




  private ImageIcon resizeIcon(String imagePath, int width, int height) {
    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
    Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImage);
  }
}
