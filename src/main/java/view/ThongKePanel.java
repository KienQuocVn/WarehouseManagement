package view;

import com.toedter.calendar.JDateChooser;
import dao.DaoLot;
import dao.DaoProduct;
import dao.DaoProductionGroup;
import dao.DaoShift;
import dao.DaoWarehouseStaff;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableCellRenderer;
import model.Lot;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import model.Product;
import model.ProductionGroup;
import model.Shift;
import model.WarehouseStaff;

public class ThongKePanel extends JPanel {
  private JTable table;
  private DefaultTableModel tableModel;
  private DaoLot daoLot;
  private DaoShift daoShift;
  private DaoProductionGroup daoProductionGroup;
  private DaoProduct daoProduct;
  private DaoWarehouseStaff daoWarehouseStaff;

  private JTextField txtSoPhieu;
  private JTextField txtKhoiLuongBi;
  private JTextField txtKhoiLuongTong;
  private JTextField txtKhoiLuongTinh;
  private JTextField txtNgaySanXuat;
  private JTextField txtSoLo;
  private JTextField txtHanSuDung;
  private JComboBox<String> productionGroupComboBox;
  private JComboBox<String> shiftComboBox1;
  private JComboBox<String> shiftComboBox;
  private JComboBox<String> warehouseStaffComboBox;
  private JComboBox<String> productionGroupComboBox1;
  private JComboBox<String> productNameComboBox;
  JDateChooser fromDateChooser = new JDateChooser();
  JDateChooser toDateChooser = new JDateChooser();
  // Lấy ngày hiện tại
  Date currentDate = new Date();
  Calendar calendar = Calendar.getInstance();

  public ThongKePanel() {
    daoLot = new DaoLot();
    daoProductionGroup = new DaoProductionGroup();
    daoShift = new DaoShift();
    daoProduct = new DaoProduct();
    daoWarehouseStaff = new DaoWarehouseStaff();

    txtSoPhieu = new JTextField();
    txtKhoiLuongBi = new JTextField();
    txtKhoiLuongTong = new JTextField();
    txtKhoiLuongTinh = new JTextField();
    txtNgaySanXuat = new JTextField();
    txtSoLo = new JTextField();
    txtHanSuDung = new JTextField();

    productionGroupComboBox = new JComboBox<>();
    shiftComboBox1 = new JComboBox<>();
    shiftComboBox = new JComboBox<>();
    warehouseStaffComboBox = new JComboBox<>();
    productionGroupComboBox1 = new JComboBox<>();
    productNameComboBox = new JComboBox<>();

    setLayout(new BorderLayout());


    // Header panel
    JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Tiêu đề
    JLabel titleLabel = new JLabel("KẾT QUẢ CẦN BÁN THÀNH PHẨM", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

    // Panel chứa bộ lọc
    JPanel filterPanel = new JPanel(new GridLayout(1, 11, 2, 2));

    // Tạo ComboBox cho Tổ Sản Xuất
    JComboBox<String> productionGroupComboBox = new JComboBox<>();
    loadProductionGroupsToComboBox(productionGroupComboBox);
    filterPanel.add(createColumn("Tổ Sản Xuất:", productionGroupComboBox));

//    productionGroupComboBox.addActionListener(e -> {
//      String selectedGroup = (String) productionGroupComboBox.getSelectedItem();
//      filterTableByProductionGroup(selectedGroup);
//    });

    JComboBox<String> shiftComboBox = new JComboBox<>();
    loadShiftsToComboBox(shiftComboBox);
    filterPanel.add(createColumn("Ca Sản Xuất:", shiftComboBox));
//    ShiftComboBox.addActionListener(e -> {
//      String selectedShift = (String) ShiftComboBox.getSelectedItem();
//      filterTableByShift(selectedShift);
//    });

    filterPanel.add(createColumn("Xuất/Nhập:", new JComboBox<>(new String[]{"Xuất", "Nhập"})));

    JComboBox<String> productNameComboBox = new JComboBox<>();
    loadProductNameToComboBox(productNameComboBox);
    filterPanel.add(createColumn("Mã Hàng:", productNameComboBox));
//    productNameComboBox.addActionListener(e -> {
//      String selectedProduct = (String) productNameComboBox.getSelectedItem();
//      filterTableByProductName(selectedProduct);
//    });


    calendar.setTime(currentDate);
    calendar.add(Calendar.MONTH, -4); // Lùi lại 2 tháng
    Date twoMonthsAgo = calendar.getTime();
    // Thiết lập ngày cho fromDateChooser và toDateChooser
    fromDateChooser.setDate(twoMonthsAgo); // Ngày lùi 2 tháng
    fromDateChooser.setDateFormatString("dd/MM/yyyy");

    toDateChooser.setDate(currentDate); // Ngày hiện tại
    toDateChooser.setDateFormatString("dd/MM/yyyy");

    // Thêm vào giao diện
    filterPanel.add(createColumn("Từ Ngày:", fromDateChooser));
    filterPanel.add(createColumn("Đến Ngày:", toDateChooser));


    JButton resetButton = new JButton(resizeIcon("/img/reset.png", 25, 25));
    filterPanel.add(createColumnBlue("Xóa Chọn:", resetButton));

// Xử lý sự kiện khi nhấn nút "Xóa chọn"
    resetButton.addActionListener(e -> {
      // Đặt lại giá trị mặc định cho các ComboBox
      productionGroupComboBox.setSelectedIndex(0); // Chọn mục đầu tiên (giả định là "Tất cả")
      shiftComboBox.setSelectedIndex(0);          // Chọn mục đầu tiên (giả định là "Tất cả")
      productNameComboBox.setSelectedIndex(0);    // Chọn mục đầu tiên (giả định là "Tất cả")

      calendar.setTime(currentDate);
      calendar.add(Calendar.MONTH, -4); // Lùi lại 4 tháng
      fromDateChooser.setDate(calendar.getTime());
      toDateChooser.setDate(currentDate);

      // Tải lại toàn bộ dữ liệu lên bảng
      loadDataToTable();
    });


    JButton searchButton = new JButton(resizeIcon("/img/search.png", 25, 25));
    filterPanel.add(createColumnBlue("Tìm Kiếm:", searchButton));

    // Khi người dùng nhấn nút "Tìm Kiếm"
    searchButton.addActionListener(e -> {
      String productionGroup = (String) productionGroupComboBox.getSelectedItem();
      productionGroup = productionGroup != null && !productionGroup.equals("Tất cả") ? productionGroup : null;

      String shift = (String) shiftComboBox.getSelectedItem();
      shift = shift != null && !shift.equals("Tất cả") ? shift : null;

      String productName = (String) productNameComboBox.getSelectedItem();
      productName = productName != null && !productName.equals("Tất cả") ? productName : null;

      java.util.Date fromDateUtil = fromDateChooser.getDate();
      java.sql.Date fromDate = fromDateUtil != null ? new java.sql.Date(fromDateUtil.getTime()) : null;

      java.util.Date toDateUtil = toDateChooser.getDate();
      java.sql.Date toDate = toDateUtil != null ? new java.sql.Date(toDateUtil.getTime()) : null;

      // Gọi phương thức tìm kiếm với các tham số đã xử lý
      List<Lot> searchResults = daoLot.searchLots(productionGroup, shift, productName, fromDate, toDate);

      // Hiển thị kết quả tìm kiếm trên bảng
      tableModel.setRowCount(0); // Xóa dữ liệu cũ
      if (searchResults != null && !searchResults.isEmpty()) {
        for (Lot lot : searchResults) {
          tableModel.addRow(new Object[]{
              lot.getLotID(),
              lot.getProduct().getProductName(),
              lot.getLotIDU(),
              lot.getProductionGroup().getGroupName(),
              lot.getShift().getShiftName(),
              lot.getProductionTime(),
              lot.getWarehouseWeight(),
              lot.getWeightDeviation(),
              lot.getWeight(),
              lot.getWarehouseStaff().getStaffName(),
              lot.getExpirationDate(),
              String.join(", ", lot.getPallets().stream()
                  .map(pallet -> pallet.getPalletID().toString())
                  .collect(Collectors.toList()))
          });
        }
      } else {
        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!");
      }
    });

    filterPanel.add(createColumn("Loại Report:", new JComboBox<>(new String[]{"Kê Nhập", "Thống Kê"})));
    filterPanel.add(createColumnBlue("Xem Trước:", new JButton(resizeIcon("/img/printer.png", 25, 25))));
    filterPanel.add(createColumnBlue("Excel:",new JButton(resizeIcon("/img/document.png", 25, 25))));


    headerPanel.add(titleLabel, BorderLayout.NORTH);
    headerPanel.add(filterPanel, BorderLayout.CENTER);
    add(headerPanel, BorderLayout.NORTH);
    add(headerPanel, BorderLayout.NORTH);

    // Table
    String[] columnNames = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Ngày Sản Xuất", "KL Cân", "KL Bì", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet", "Xuất/Nhập"};
    tableModel = new DefaultTableModel(columnNames, 0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    table.setRowHeight(25);
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
    table.setFont(new Font("Arial", Font.PLAIN, 12));
    // Thêm sự kiện click chuột vào bảng
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int selectedRow = table.getSelectedRow(); // Lấy chỉ số dòng được chọn
        if (selectedRow != -1) { // Kiểm tra có dòng nào được chọn
          loadDataToFields(selectedRow); // Gọi phương thức load data vào các textfield
        }
      }
    });
    add(scrollPane, BorderLayout.CENTER);


    // Footer Panel
    JPanel footerPanel = new JPanel(new BorderLayout());

    // Sử dụng GridBagLayout cho summaryPanel
    JPanel summaryPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 2, 2, 2);
    gbc.fill = GridBagConstraints.HORIZONTAL; // Giãn ngang
    gbc.weightx = 1.0; // Giãn toàn bộ chiều ngang
    gbc.weighty = 0;   // Không cần giãn chiều dọc

    // ====== HÀNG 1 ======
    gbc.gridx = 0; // Cột đầu tiên
    gbc.gridy = 0; // Hàng đầu tiên
    gbc.gridwidth = 1; // Chiếm 1 cột
    summaryPanel.add(createColumnYellow("Số Phiếu:",txtSoPhieu), gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    summaryPanel.add(createColumn("Khối Lượng Bì:",txtKhoiLuongBi), gbc);

    gbc.gridx = 2;
    summaryPanel.add(createColumnYellow("Khối Lượng Tổng:", txtKhoiLuongTong), gbc);

    gbc.gridx = 3;
    summaryPanel.add(createColumn("Khối Lượng Tịnh:", txtKhoiLuongTinh), gbc);

    gbc.gridx = 4;
    summaryPanel.add(createColumnYellow("Ngày Sản Xuất:", txtNgaySanXuat), gbc);

    gbc.gridx = 5;
    warehouseStaffComboBox = new JComboBox<>();
    loadWarehouseStaffToComboBox(warehouseStaffComboBox);
    summaryPanel.add(createColumn("Thủ Kho:", warehouseStaffComboBox), gbc);

    gbc.gridx = 6;
    summaryPanel.add(createColumnBlue("Lưu Thay Đổi:",new JButton(resizeIcon("/img/diskette.png", 25, 25))),gbc);

    gbc.gridx = 7;
    summaryPanel.add(createColumnRed("Xóa Dữ Liệu:", new JButton(resizeIcon("/img/trash-bin.png", 25, 25))),gbc);

    // ====== HÀNG 2 ======
    gbc.gridx = 0; // Cột đầu tiên
    gbc.gridy = 1; // Hàng thứ hai
    gbc.gridwidth = 2; // Chiếm 2 cột
    summaryPanel.add(createColumn("Tên Đơn Hàng:", new JComboBox<>(new String[]{"Tất cả", "Tổ 1", "Tổ 2"})), gbc);

    gbc.gridx = 2; // Cột thứ ba
    gbc.gridwidth = 1; // Trở lại chiếm 1 cột
    summaryPanel.add(createColumn("Số Lô:", txtSoLo), gbc);

    gbc.gridx = 3; // Cột thứ tư
    summaryPanel.add(createColumn("Hạn Sử Dụng(Ngày):",txtHanSuDung), gbc);

    gbc.gridx = 4; // Cột thứ năm
    productionGroupComboBox1 = new JComboBox<>();
    loadProductionGroupsToComboBox(productionGroupComboBox1);
    summaryPanel.add(createColumn("Tổ Sản Xuất:", productionGroupComboBox1),gbc);

    gbc.gridx = 5; // Cột thứ sáu
    shiftComboBox1 = new JComboBox<>();
    loadShiftsToComboBox(shiftComboBox1);
    summaryPanel.add(createColumn("Ca Sản Xuất:",shiftComboBox1), gbc);

    gbc.gridx = 6; // Cột thứ bảy
    summaryPanel.add(createColumnBlue("In Phiếu:",  new JButton(resizeIcon("/img/printer.png", 25, 25))),gbc);

    gbc.gridx = 7; // Cột thứ tám
    summaryPanel.add(createColumnRed("Xóa Tất Cả:",new JButton(resizeIcon("/img/trash-bin.png", 25, 25))),gbc);

    footerPanel.add(summaryPanel, BorderLayout.CENTER);

    add(footerPanel, BorderLayout.SOUTH);
    loadDataToTable();

  }


  //==========================================LOAD_DATA_TABLE==========================================
  private void loadDataToTable() {
    tableModel.setRowCount(0); // Xóa dữ liệu hiện tại trong bảng
    List<Lot> lots = daoLot.selectAll(); // Lấy dữ liệu từ cơ sở dữ liệu

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the date format

    for (Lot lot : lots) {
      String productionTimeFormatted = lot.getProductionTime() != null ? lot.getProductionTime().format(formatter) : "";
      String expirationDaysFormatted = lot.getExpirationDate() != null ? lot.getExpirationDate().format(formatter) : "";

      // Lấy danh sách PalletID và chuyển thành chuỗi
      String palletIDs = lot.getPallets().stream()
          .map(pallet -> String.valueOf(pallet.getPalletID()))
          .collect(Collectors.joining(", "));

      Object[] row = {
          lot.getLotID(),                               // "LotID"
          lot.getProduct().getProductName(),            // "ProductName"
          lot.getLotIDU(),                              // "LotIDU"
          lot.getProductionGroup().getGroupName(),      // "GroupName"
          lot.getShift().getShiftName(),                // "ShiftName"
          productionTimeFormatted,                      // "ProductionTime" (formatted)
          lot.getWarehouseWeight(),                     // "WarehouseWeight"
          lot.getWeightDeviation(),                     // "WeightDeviation"
          lot.getWeight(),                              // "Weight"
          lot.getWarehouseStaff().getStaffName(),       // "StaffName"
          expirationDaysFormatted,                      // "ExpirationDays" (formatted)
          palletIDs                                     // "PalletIDs"
      };
      tableModel.addRow(row);
    }
  }


  //==========================================FILTER_COMBOBOX=========================================

  // Phương thức lọc bảng dựa trên tổ sản xuất
  private void filterTableByProductionGroup(String selectedGroup) {
    // Xóa toàn bộ dữ liệu cũ trong bảng
    tableModel.setRowCount(0);

    // Lấy danh sách tất cả các lô hàng từ database
    List<Lot> lots = daoLot.selectAll();

    for (Lot lot : lots) {
      // Kiểm tra nếu "Tổ Sản Xuất" phù hợp với lựa chọn hoặc người dùng chọn "Tất cả"
      if (selectedGroup.equals("Tất cả") || lot.getProductionGroup().getGroupName().equals(selectedGroup)) {
        String palletIDs = lot.getPallets().stream()
            .map(pallet -> String.valueOf(pallet.getPalletID()))
            .collect(Collectors.joining(", "));
        // Thêm hàng vào bảng
        tableModel.addRow(new Object[]{
            lot.getLotID(),                               // "LotID"
            lot.getProduct().getProductName(),            // "ProductID"
            lot.getLotIDU(),                              // "LotIDU"
            lot.getProductionGroup().getGroupName(),      // "GroupID"
            lot.getShift().getShiftName(),                // "ShiftID"
            lot.getProductionTime(),                      // "ProductionTime"
            lot.getWarehouseWeight(),                     // "WarehouseWeight"
            lot.getWeightDeviation(),                     // "WeightDeviation"
            lot.getWeight(),                              // "Weight"
            lot.getWarehouseStaff().getStaffName(),       // "StaffName"
            lot.getExpirationDate(),                      // "ExpirationDays"
            palletIDs
        });
      }
    }
  }

  private void filterTableByProductName(String selectedProduct) {
    // Xóa toàn bộ dữ liệu cũ trong bảng
    tableModel.setRowCount(0);

    // Lấy danh sách tất cả các lô hàng từ database
    List<Lot> lots = daoLot.selectAll();

    for (Lot lot : lots) {
      // Kiểm tra nếu "Mã Hàng" phù hợp với lựa chọn hoặc người dùng chọn "Tất cả"
      if (selectedProduct.equals("Tất cả") || lot.getProduct().getProductName().equals(selectedProduct)) {
        String palletIDs = lot.getPallets().stream()
            .map(pallet -> String.valueOf(pallet.getPalletID()))
            .collect(Collectors.joining(", "));
        // Thêm hàng vào bảng
        tableModel.addRow(new Object[]{
            lot.getLotID(),                               // "LotID"
            lot.getProduct().getProductName(),            // "ProductID"
            lot.getLotIDU(),                              // "LotIDU"
            lot.getProductionGroup().getGroupName(),      // "GroupID"
            lot.getShift().getShiftName(),                // "ShiftID"
            lot.getProductionTime(),                      // "ProductionTime"
            lot.getWarehouseWeight(),                     // "WarehouseWeight"
            lot.getWeightDeviation(),                     // "WeightDeviation"
            lot.getWeight(),                              // "Weight"
            lot.getWarehouseStaff().getStaffName(),       // "StaffName"
            lot.getExpirationDate(),                      // "ExpirationDays"
            palletIDs
        });
      }
    }
  }

  private void filterTableByShift(String selectedShift) {
    // Xóa toàn bộ dữ liệu cũ trong bảng
    tableModel.setRowCount(0);

    // Lấy danh sách tất cả các lô hàng từ database
    List<Lot> lots = daoLot.selectAll();

    for (Lot lot : lots) {
      // Kiểm tra nếu "Ca Sản Xuất" phù hợp với lựa chọn hoặc người dùng chọn "Tất cả"
      if (selectedShift.equals("Tất cả") || lot.getShift().getShiftName().equals(selectedShift)) {
        String palletIDs = lot.getPallets().stream()
            .map(pallet -> String.valueOf(pallet.getPalletID()))
            .collect(Collectors.joining(", "));
        // Thêm hàng vào bảng
        tableModel.addRow(new Object[]{
            lot.getLotID(),                               // "LotID"
            lot.getProduct().getProductName(),            // "ProductID"
            lot.getLotIDU(),                              // "LotIDU"
            lot.getProductionGroup().getGroupName(),      // "GroupID"
            lot.getShift().getShiftName(),                // "ShiftID"
            lot.getProductionTime(),                      // "ProductionTime"
            lot.getWarehouseWeight(),                     // "WarehouseWeight"
            lot.getWeightDeviation(),                     // "WeightDeviation"
            lot.getWeight(),                              // "Weight"
            lot.getWarehouseStaff().getStaffName(),       // "StaffName"
            lot.getExpirationDate(),                      // "ExpirationDays"
            palletIDs
        });
      }
    }
  }


  //==========================================LOAD_DATA_CLICK_TABLE==========================================

  private void loadDataToFields(int selectedRow) {
    // Lấy dữ liệu từ bảng và chuyển thành String
    String soPhieu = tableModel.getValueAt(selectedRow, 0) != null
        ? tableModel.getValueAt(selectedRow, 0).toString()
        : ""; // Số Phiếu
    String soLo = tableModel.getValueAt(selectedRow, 2) != null
        ? tableModel.getValueAt(selectedRow, 2).toString()
        : ""; // Số Lô
    String khoiLuongBi = tableModel.getValueAt(selectedRow, 6) != null
        ? tableModel.getValueAt(selectedRow, 6).toString()
        : ""; // Khối Lượng Bì
    String khoiLuongTong = tableModel.getValueAt(selectedRow, 7) != null
        ? tableModel.getValueAt(selectedRow, 7).toString()
        : ""; // Khối Lượng Tổng
    String khoiLuongTinh = tableModel.getValueAt(selectedRow, 8) != null
        ? tableModel.getValueAt(selectedRow, 8).toString()
        : ""; // Khối Lượng Tịnh
    String ngaySanXuat = tableModel.getValueAt(selectedRow, 5) != null
        ? tableModel.getValueAt(selectedRow, 5).toString()
        : ""; // Ngày Sản Xuất
    String hanSuDung = tableModel.getValueAt(selectedRow, 10) != null
        ? tableModel.getValueAt(selectedRow, 10).toString()
        : ""; // Hạn Sử Dụng
    String thuKho = tableModel.getValueAt(selectedRow, 9) != null
        ? tableModel.getValueAt(selectedRow, 9).toString()
        : ""; // Thủ Kho
    String toSanXuat = tableModel.getValueAt(selectedRow, 3) != null
        ? tableModel.getValueAt(selectedRow, 3).toString()
        : ""; // Tổ Sản Xuất
    String caSanXuat = tableModel.getValueAt(selectedRow, 4) != null
        ? tableModel.getValueAt(selectedRow, 4).toString()
        : ""; // Ca Sản Xuất

    // Điền dữ liệu vào các JTextField
    txtSoPhieu.setText(soPhieu);
    txtKhoiLuongBi.setText(khoiLuongBi);
    txtKhoiLuongTong.setText(khoiLuongTong);
    txtKhoiLuongTinh.setText(khoiLuongTinh);
    txtNgaySanXuat.setText(ngaySanXuat);
    txtSoLo.setText(soLo);
    txtHanSuDung.setText(hanSuDung);

    // Set giá trị cho các JComboBox
    warehouseStaffComboBox.setSelectedItem(thuKho);
    productionGroupComboBox1.setSelectedItem(toSanXuat);
    shiftComboBox1.setSelectedItem(caSanXuat);
  }


  //==========================================LOADDATACOMBOBOX==========================================

  // Phương thức tải dữ liệu vào JComboBox ProductionGroup
  private void loadProductionGroupsToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems(); // Xóa các mục cũ (nếu có)
    comboBox.addItem("Tất cả"); // Thêm tùy chọn mặc định

    // Lấy danh sách ProductionGroup từ database
    List<ProductionGroup> groups = daoProductionGroup.selectAll();
    for (ProductionGroup group : groups) {
      comboBox.addItem(group.getGroupName());
    }
  }

  // Phương thức tải dữ liệu vào JComboBox Shifts
  private void loadShiftsToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems(); // Xóa các mục cũ (nếu có)
    comboBox.addItem("Tất cả"); // Thêm tùy chọn mặc định

    // Lấy danh sách shifts từ database
    List<Shift> shifts = daoShift.selectAll();
    for (Shift shift : shifts) {
      comboBox.addItem(shift.getShiftName());
    }
  }

  // Phương thức tải dữ liệu vào JComboBox ProductName
  private void loadProductNameToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems(); // Xóa các mục cũ (nếu có)
    comboBox.addItem("Tất cả"); // Thêm tùy chọn mặc định

    // Lấy danh sách product từ database
    List<Product> products = daoProduct.selectAll();
    for (Product product : products) {
      comboBox.addItem(product.getProductName());
    }
  }

  // Phương thức tải dữ liệu vào JComboBox ProductName
  private void loadWarehouseStaffToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems(); // Xóa các mục cũ (nếu có)
    comboBox.addItem("Tất cả"); // Thêm tùy chọn mặc định

    // Lấy danh sách product từ database
    List<WarehouseStaff> warehouseStaffs = daoWarehouseStaff.selectAll();
    for (WarehouseStaff warehouseStaff : warehouseStaffs) {
      comboBox.addItem(warehouseStaff.getStaffName());
    }
  }



  //==========================================CREATECOlUMN==========================================

  private JPanel createColumn(String labelText, JComponent inputComponent) {
    JPanel columnPanel = new JPanel(new BorderLayout(5, 5)); // Sử dụng BorderLayout để căn chỉnh
    columnPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền màu xám
    JLabel label = new JLabel(labelText, JLabel.LEFT); // Tiêu đề cột
    label.setFont(new Font("Arial", Font.BOLD, 15));
    label.setHorizontalAlignment(SwingConstants.LEFT); // Căn giữa văn bản trong JLabel
    columnPanel.add(label, BorderLayout.NORTH); // Thêm JLabel ở trên
    columnPanel.add(inputComponent, BorderLayout.CENTER); // Thêm thành phần nhập liệu ở dưới
    return columnPanel;
  }
  private JPanel createColumnYellow(String labelText, JComponent inputComponent) {
    JPanel columnPanel = new JPanel(new BorderLayout(5, 5)); // Sử dụng BorderLayout để căn chỉnh
    columnPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền màu xám

    JLabel label = new JLabel(labelText, JLabel.LEFT); // Tiêu đề cột
    label.setFont(new Font("Arial", Font.BOLD, 15));
    label.setHorizontalAlignment(SwingConstants.LEFT); // Căn giữa văn bản trong JLabel
    columnPanel.add(label, BorderLayout.NORTH); // Thêm JLabel ở trên

    // Kiểm tra nếu inputComponent là JTextField
    if (inputComponent instanceof JTextField) {
      JTextField textField = (JTextField) inputComponent;
      textField.setBackground(new Color(253, 243, 212));
      textField.setFont(new Font("Arial", Font.PLAIN, 15)); // Phông chữ
      textField.setBorder(BorderFactory.createLineBorder(new Color(253, 243, 212))); // Viền
    }

    columnPanel.add(inputComponent, BorderLayout.CENTER); // Thêm thành phần nhập liệu ở dưới
    return columnPanel;
  }

  private JPanel createColumnBlue(String labelText, JComponent inputComponent) {
    JPanel columnPanel = new JPanel(new BorderLayout(5, 5)); // Sử dụng BorderLayout để căn chỉnh
    columnPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền màu xám

    JLabel label = new JLabel(labelText, JLabel.LEFT); // Tiêu đề cột
    label.setFont(new Font("Arial", Font.BOLD, 15));
    label.setHorizontalAlignment(SwingConstants.LEFT); // Căn giữa văn bản trong JLabel
    columnPanel.add(label, BorderLayout.NORTH); // Thêm JLabel ở trên

    // Kiểm tra nếu inputComponent là JButton
    if (inputComponent instanceof JButton) {
      JButton button = (JButton) inputComponent;
      button.setBackground(new Color(70, 130, 180));
      button.setForeground(Color.WHITE); // Màu chữ trắng
      button.setFont(new Font("Arial", Font.BOLD, 15)); // Phông chữ
      button.setFocusPainted(false); // Tắt viền khi button được chọn
      button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180))); // Viền
    }

    columnPanel.add(inputComponent, BorderLayout.CENTER); // Thêm thành phần nhập liệu ở dưới
    return columnPanel;
  }

  private JPanel createColumnRed(String labelText, JComponent inputComponent) {
    JPanel columnPanel = new JPanel(new BorderLayout(5, 5)); // Sử dụng BorderLayout để căn chỉnh
    columnPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền màu xám

    JLabel label = new JLabel(labelText, JLabel.LEFT); // Tiêu đề cột
    label.setFont(new Font("Arial", Font.BOLD, 15));
    label.setHorizontalAlignment(SwingConstants.LEFT); // Căn giữa văn bản trong JLabel
    columnPanel.add(label, BorderLayout.NORTH); // Thêm JLabel ở trên

    // Kiểm tra nếu inputComponent là JButton
    if (inputComponent instanceof JButton) {
      JButton button = (JButton) inputComponent;
      button.setBackground(new Color(193, 63, 63));
      button.setForeground(Color.WHITE); // Màu chữ trắng
      button.setFont(new Font("Arial", Font.BOLD, 15)); // Phông chữ
      button.setFocusPainted(false); // Tắt viền khi button được chọn
      button.setBorder(BorderFactory.createLineBorder(new Color(193, 63, 63)));
    }

    columnPanel.add(inputComponent, BorderLayout.CENTER); // Thêm thành phần nhập liệu ở dưới
    return columnPanel;
  }


  private ImageIcon resizeIcon(String imagePath, int width, int height) {
    ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
    Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImage);
  }
}
