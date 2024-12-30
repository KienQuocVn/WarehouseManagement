package view;

import com.toedter.calendar.JDateChooser;
import dao.DaoLot;
import dao.DaoProduct;
import dao.DaoProductionGroup;
import dao.DaoShift;
import dao.DaoWarehouseStaff;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private JComboBox<String> productNameComboBox1;
  JDateChooser fromDateChooser = new JDateChooser();
  JDateChooser toDateChooser = new JDateChooser();
  JDateChooser productionDateChooser = new JDateChooser();
  JDateChooser expirationDateChooser = new JDateChooser();
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
    productionGroupComboBox1 = new JComboBox<>();
    shiftComboBox1 = new JComboBox<>();
    shiftComboBox = new JComboBox<>();
    warehouseStaffComboBox = new JComboBox<>();
    productNameComboBox = new JComboBox<>();
    productNameComboBox1 = new JComboBox<>();

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

      Date fromDateUtil = fromDateChooser.getDate();
      LocalDate fromDate = fromDateUtil != null ? new java.sql.Date(fromDateUtil.getTime()).toLocalDate()
          : null;

      Date toDateUtil = toDateChooser.getDate();
      LocalDate  toDate = toDateUtil != null ? new java.sql.Date(toDateUtil.getTime()).toLocalDate()
          : null;

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
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          loadDataToFields(selectedRow);
        }
      }
    });
    add(scrollPane, BorderLayout.CENTER);


    JPanel footerPanel = new JPanel(new BorderLayout());

    JPanel summaryPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 2, 2, 2);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 0;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
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
    productionDateChooser.setDate(currentDate);
    productionDateChooser.setDateFormatString("dd/MM/yyyy");
    summaryPanel.add(createColumnYellow("Ngày Sản Xuất:", productionDateChooser), gbc);

    gbc.gridx = 5;
    warehouseStaffComboBox = new JComboBox<>();
    loadWarehouseStaffToComboBox(warehouseStaffComboBox);
    summaryPanel.add(createColumn("Thủ Kho:", warehouseStaffComboBox), gbc);

    gbc.gridx = 6;
    JButton updateButton = new JButton(resizeIcon("/img/diskette.png", 25, 25));
    updateButton.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        try {
          // Lấy giá trị từ TextField
          String soPhieu = txtSoPhieu.getText().trim();
          BigDecimal khoiLuongBi = new BigDecimal(txtKhoiLuongBi.getText().trim());
          BigDecimal khoiLuongTong = new BigDecimal(txtKhoiLuongTong.getText().trim());
          BigDecimal khoiLuongTinh = new BigDecimal(txtKhoiLuongTinh.getText().trim());

          // Parse ngày tháng
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
          LocalDate ngaySanXuat = LocalDate.parse(txtNgaySanXuat.getText().trim(), formatter);
          LocalDate hanSuDung = LocalDate.parse(txtHanSuDung.getText().trim(), formatter);

          // Lấy ID từ ComboBox thông qua Map
          Integer selectedProductID = productMap.get(productNameComboBox1.getSelectedItem());
          Integer selectedProductGroupID = productionGroupMap.get(productionGroupComboBox1.getSelectedItem());
          Integer selectedShiftID = shiftMap.get(shiftComboBox1.getSelectedItem());
          Integer selectedStaffID = warehouseStaffMap.get(warehouseStaffComboBox.getSelectedItem());

          // Lấy LotID từ bảng
          int lotId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
          Lot lotToUpdate = daoLot.selectbyID(lotId);

          // Cập nhật thông tin vào đối tượng Lot
          if (lotToUpdate != null) {
            lotToUpdate.setLotIDU(soPhieu);
            lotToUpdate.setWeightDeviation(khoiLuongBi);
            lotToUpdate.setWarehouseWeight(khoiLuongTong);
            lotToUpdate.setWeight(khoiLuongTinh);
            lotToUpdate.setProductionTime(LocalDate.from(ngaySanXuat.atStartOfDay()));
            lotToUpdate.setExpirationDate(LocalDate.from(hanSuDung.atStartOfDay()));

            if (selectedProductID != null) {
              Product product = daoProduct.selectbyID(selectedProductID);
              lotToUpdate.setProduct(product);
            }

            if (selectedProductGroupID != null) {
              ProductionGroup group = daoProductionGroup.selectbyID(selectedProductGroupID);
              lotToUpdate.setProductionGroup(group);
            }

            if (selectedShiftID != null) {
              Shift shift = daoShift.selectbyID(selectedShiftID);
              lotToUpdate.setShift(shift);
            }

            if (selectedStaffID != null) {
              WarehouseStaff staff = daoWarehouseStaff.selectbyID(selectedStaffID);
              lotToUpdate.setWarehouseStaff(staff);
            }

            daoLot.update(lotToUpdate);
            txtSoPhieu.setText("");
            txtKhoiLuongBi.setText("");
            txtKhoiLuongTong.setText("");
            txtKhoiLuongTinh.setText("");
            txtNgaySanXuat.setText("");
            txtSoLo.setText("");
            txtHanSuDung.setText("");
            warehouseStaffComboBox.setSelectedItem("Tất cả");
            productionGroupComboBox1.setSelectedItem("Tất cả");
            shiftComboBox1.setSelectedItem("Tất cả");
            productNameComboBox1.setSelectedItem("Tất cả");
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
            loadDataToTable();
          }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + ex.getMessage());
          System.out.println(ex.getMessage());
          ex.printStackTrace();
        }
      } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để cập nhật.");
      }
    });

    summaryPanel.add(createColumnBlue("Lưu Thay Đổi:",updateButton),gbc);

    gbc.gridx = 7;
    JButton deleteButton = new JButton(resizeIcon("/img/trash-bin.png", 25, 25));
    deleteButton.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        int lotId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa LotID: " + lotId + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
          // Lấy ngày hiện tại
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
          String currentDate = dateFormat.format(new Date());
          daoLot.deleteLotById(lotId);
          loadDataToTable();
          txtSoPhieu.setText("");
          txtKhoiLuongBi.setText("");
          txtKhoiLuongTong.setText("");
          txtKhoiLuongTinh.setText("");
          txtNgaySanXuat.setText(currentDate);
          txtSoLo.setText("");
          txtHanSuDung.setText(currentDate);
          warehouseStaffComboBox.setSelectedItem("Tất cả");
          productionGroupComboBox1.setSelectedItem("Tất cả");
          shiftComboBox1.setSelectedItem("Tất cả");

          JOptionPane.showMessageDialog(null, "Xóa thành công LotID: " + lotId);
        }
      } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa.");
      }
    });

    summaryPanel.add(createColumnRed("Xóa Dữ Liệu:", deleteButton), gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    productNameComboBox1 = new JComboBox<>();
    loadProductNameToComboBox(productNameComboBox1);
    summaryPanel.add(createColumn("Mã Hàng:", productNameComboBox1), gbc);

    gbc.gridx = 2;
    gbc.gridwidth = 1;
    summaryPanel.add(createColumn("Số Lô:", txtSoLo), gbc);

    gbc.gridx = 3;
    expirationDateChooser.setDate(currentDate);
    expirationDateChooser.setDateFormatString("dd/MM/yyyy");
    summaryPanel.add(createColumn("Hạn Sử Dụng(Ngày):", expirationDateChooser), gbc);

    gbc.gridx = 4;
    productionGroupComboBox1 = new JComboBox<>();
    loadProductionGroupsToComboBox(productionGroupComboBox1);
    summaryPanel.add(createColumn("Tổ Sản Xuất:", productionGroupComboBox1),gbc);

    gbc.gridx = 5;
    shiftComboBox1 = new JComboBox<>();
    loadShiftsToComboBox(shiftComboBox1);
    summaryPanel.add(createColumn("Ca Sản Xuất:",shiftComboBox1), gbc);

    gbc.gridx = 6;
    summaryPanel.add(createColumnBlue("In Phiếu:",  new JButton(resizeIcon("/img/printer.png", 25, 25))),gbc);

    gbc.gridx = 7;
    JButton deleteAllButton = new JButton(resizeIcon("/img/trash-bin.png", 25, 25));
    deleteAllButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tất cả dữ liệu?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        daoLot.deleteAllLots();
        loadDataToTable();
        JOptionPane.showMessageDialog(null, "Đã xóa tất cả dữ liệu.");
      }
    });
    summaryPanel.add(createColumnRed("Xóa Tất Cả:", deleteAllButton), gbc);

    footerPanel.add(summaryPanel, BorderLayout.CENTER);

    add(footerPanel, BorderLayout.SOUTH);
    loadDataToTable();

  }


  private void loadDataToTable() {
    tableModel.setRowCount(0);
    List<Lot> lots = daoLot.selectAll();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the date format

    for (Lot lot : lots) {
      String productionTimeFormatted = lot.getProductionTime() != null ? lot.getProductionTime().format(formatter) : "";
      String expirationDaysFormatted = lot.getExpirationDate() != null ? lot.getExpirationDate().format(formatter) : "";
      String shiftName = "N/A";
      if (lot.getShift() != null && lot.getShift().getShiftName() != null) {
        shiftName = lot.getShift().getShiftName();
      }
      String productName = "N/A";
      if (lot.getProduct() != null && lot.getProduct().getProductName() != null) {
        productName = lot.getProduct().getProductName();
      }
      String productGroupName = "N/A";
      if (lot.getProductionGroup() != null && lot.getProductionGroup().getGroupName() != null) {
        productGroupName = lot.getProductionGroup().getGroupName();
      }
      String staffName = "N/A";
      if (lot.getWarehouseStaff() != null && lot.getWarehouseStaff().getStaffName() != null) {
        staffName = lot.getWarehouseStaff().getStaffName();
      }
      String palletIDs = lot.getPallets().stream()
          .map(pallet -> String.valueOf(pallet.getPalletID()))
          .collect(Collectors.joining(", ")); // Nối các PalletID bằng dấu phẩy

      Object[] row = {
          lot.getLotID(),
          productName,
          lot.getLotIDU(),
          productGroupName,
          shiftName,
          productionTimeFormatted,
          lot.getWarehouseWeight(),
          lot.getWeightDeviation(),
          lot.getWeight(),
          staffName,
          expirationDaysFormatted,
          palletIDs
      };

      tableModel.addRow(row);
    }
  }

  private void loadDataToFields(int selectedRow) {
    String soPhieu = tableModel.getValueAt(selectedRow, 0) != null
        ? tableModel.getValueAt(selectedRow, 0).toString()
        : "";
    String mahang = tableModel.getValueAt(selectedRow, 1) != null
        ? tableModel.getValueAt(selectedRow, 1).toString()
        : "N/A";
    String soLo = tableModel.getValueAt(selectedRow, 2) != null
        ? tableModel.getValueAt(selectedRow, 2).toString()
        : "";
    String toSanXuat = tableModel.getValueAt(selectedRow, 3) != null
        ? tableModel.getValueAt(selectedRow, 3).toString()
        : "N/A";
    String caSanXuat = tableModel.getValueAt(selectedRow, 4) != null
        ? tableModel.getValueAt(selectedRow, 4).toString()
        : "N/A";
    String ngaySanXuat = tableModel.getValueAt(selectedRow, 5) != null
        ? tableModel.getValueAt(selectedRow, 5).toString()
        : "";
    String khoiLuongBi = tableModel.getValueAt(selectedRow, 7) != null
        ? tableModel.getValueAt(selectedRow, 7).toString()
        : "";
    String khoiLuongTong = tableModel.getValueAt(selectedRow, 6) != null
        ? tableModel.getValueAt(selectedRow, 6).toString()
        : "";
    String khoiLuongTinh = tableModel.getValueAt(selectedRow, 8) != null
        ? tableModel.getValueAt(selectedRow, 8).toString()
        : "";
    String thuKho = tableModel.getValueAt(selectedRow, 9) != null
        ? tableModel.getValueAt(selectedRow, 9).toString()
        : "N/A";
    String hanSuDung = tableModel.getValueAt(selectedRow, 10) != null
        ? tableModel.getValueAt(selectedRow, 10).toString()
        : "";

    txtSoPhieu.setText(soPhieu);
    txtKhoiLuongBi.setText(khoiLuongBi);
    txtKhoiLuongTong.setText(khoiLuongTong);
    txtKhoiLuongTinh.setText(khoiLuongTinh);
    txtNgaySanXuat.setText(ngaySanXuat);
    txtSoLo.setText(soLo);
    txtHanSuDung.setText(hanSuDung);

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

      if (!ngaySanXuat.isEmpty()) {
        LocalDate productionDate = LocalDate.parse(ngaySanXuat, formatter);
        productionDateChooser.setDate(java.sql.Date.valueOf(productionDate));
      }

      if (!hanSuDung.isEmpty()) {
        LocalDate expirationDate = LocalDate.parse(hanSuDung, formatter);
        expirationDateChooser.setDate(java.sql.Date.valueOf(expirationDate));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật ngày.");
    }

    productNameComboBox1.setSelectedItem(mahang);
    warehouseStaffComboBox.setSelectedItem(thuKho);
    productionGroupComboBox1.setSelectedItem(toSanXuat);
    shiftComboBox1.setSelectedItem(caSanXuat);
  }



  private Map<String, Integer> productionGroupMap = new HashMap<>();

  private void loadProductionGroupsToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems();
    comboBox.addItem("Tất cả");

    List<ProductionGroup> groups = daoProductionGroup.selectAll();
    productionGroupMap.clear();
    for (ProductionGroup group : groups) {
      if (group != null && group.getGroupName() != null) {
        comboBox.addItem(group.getGroupName());
        productionGroupMap.put(group.getGroupName(), group.getGroupID());
      }
    }
  }


  private Map<String, Integer> shiftMap = new HashMap<>();
  private void loadShiftsToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems();
    comboBox.addItem("Tất cả");

    List<Shift> shifts = daoShift.selectAll();
    shiftMap.clear();
    for (Shift shift : shifts) {
      if (shift != null && shift.getShiftName() != null) {
        comboBox.addItem(shift.getShiftName());
        shiftMap.put(shift.getShiftName(), shift.getShiftId());
      }
    }
  }

  private Map<String, Integer> productMap = new HashMap<>();
  private void loadProductNameToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems();
    comboBox.addItem("Tất cả");

    List<Product> products = daoProduct.selectAll();
    productMap.clear();
    for (Product product : products) {
      if (product != null && product.getProductName() != null) {
        comboBox.addItem(product.getProductName());

        productMap.put(product.getProductName(), product.getProductID());
      }
    }
  }

  private Map<String, Integer> warehouseStaffMap = new HashMap<>();
  private void loadWarehouseStaffToComboBox(JComboBox<String> comboBox) {
    comboBox.removeAllItems();
    comboBox.addItem("Tất cả");

    List<WarehouseStaff> warehouseStaffs = daoWarehouseStaff.selectAll();
    warehouseStaffMap.clear();
    for (WarehouseStaff warehouseStaff : warehouseStaffs) {
      if (warehouseStaff != null && warehouseStaff.getStaffName() != null) {
        comboBox.addItem(warehouseStaff.getStaffName());
        warehouseStaffMap.put(warehouseStaff.getStaffName(), warehouseStaff.getStaffId());
      }
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
        });
      }
    }
  }



  //==========================================CREATECOlUMN==========================================

  private JPanel createColumn(String labelText, JComponent inputComponent) {
    JPanel columnPanel = new JPanel(new BorderLayout(5, 5)); // Sử dụng BorderLayout để căn chỉnh
    columnPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền màu xám
    JLabel label = new JLabel(labelText, JLabel.LEFT); // Tiêu đề cột
    label.setFont(new Font("Arial", Font.BOLD, 15));
    label.setHorizontalAlignment(SwingConstants.LEFT); // Căn giữa văn bản trong JLabel
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
