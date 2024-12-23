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

    // New panel above headerPanel
    JPanel softwarePanel = new JPanel();
    softwarePanel.setBackground(Color.white);
    softwarePanel.setBorder(new RoundedBorder(0));

    JLabel softwareLabel = new JLabel("PHẦN MỀM CÂN XUẤT NHẬP KHO");
    softwareLabel.setVerticalAlignment(SwingConstants.TOP); // Căn lên trên
    softwareLabel.setFont(new Font("Arial", Font.BOLD, 16));
    softwarePanel.add(softwareLabel);

    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBackground(Color.WHITE);

    containerPanel.setPreferredSize(new Dimension(0, 40)); // Đặt chiều cao cho panel
    containerPanel.add(softwarePanel, BorderLayout.CENTER);
    // header
    add(containerPanel, BorderLayout.NORTH);

    // Header panel with space between panels
    JPanel headerPanel = new JPanel(new GridLayout(1, 3, 6, 0)); // 10px gap between panels (columns)
    headerPanel.setBorder(new EmptyBorder(6, 4, 6, 4)); // Optional: margin around headerPanel
    // Left panel: Thông Tin Mã Hàng
    JPanel leftPanel = new JPanel(new GridLayout(7, 2, 5, 5));

    leftPanel.setBorder(new RoundedBorder(20));
    leftPanel.setBackground(Color.WHITE);


    leftPanel.add(new JLabel("Mã Hàng:"));
    leftPanel.add(new JComboBox<>(new String[]{"Đơn 1", "Đơn 2", "Đơn 3"}));

    leftPanel.add(new JLabel("Màu:"));
    leftPanel.add(new JTextField("Xanh"));

    leftPanel.add(new JLabel("Hạn SD (Ngày):"));
    leftPanel.add(new JTextField("4.0"));

    leftPanel.add(new JLabel("Số Lô:"));
    leftPanel.add(new JTextField("0408N"));

    leftPanel.add(new JLabel("BL Bi (Kg):"));
    leftPanel.add(new JTextField("0408N"));

    leftPanel.add(new JLabel("Printer:"));
    JComboBox<String> printerBox = new JComboBox<>(new String[]{"Foxit PhantomPDF Printer"});
    leftPanel.add(printerBox);
    leftPanel.add(new JButton("In"));

    headerPanel.add(leftPanel);

    // Center panel: Thông Tin Sản Xuất
    JPanel centerPanel = new JPanel(new GridLayout(6, 2, 5, 5));
    centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    centerPanel.setBorder(new RoundedBorder(20));


    centerPanel.add(new JLabel("Tổ Sản Xuất:"));
    centerPanel.add(new JComboBox<>(new String[]{"T? 1"}));

    centerPanel.add(new JLabel("Ca Sản Xuất:"));
    centerPanel.add(new JComboBox<>(new String[]{"CA NGÀY"}));

    centerPanel.add(new JLabel("Thủ Kho:"));
    centerPanel.add(new JComboBox<>(new String[]{"CA NGÀY"}));

    centerPanel.add(new JLabel("Số Pallet:"));
    centerPanel.add(new JTextField("Me01"));

    centerPanel.add(new JLabel("Tìm Kiếm mã hàng và số lô:"));
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.add(new JTextField(10));
    searchPanel.add(new JButton("Tìm"));
    searchPanel.add(new JButton("Làm Mới"));
    centerPanel.add(searchPanel);

    headerPanel.add(centerPanel);

    // Right panel: Khối Lượng Cân
    JPanel rightPanel = new JPanel(new GridLayout(4, 1, 5, 5));
    rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    rightPanel.setBorder(new RoundedBorder(20));

    rightPanel.add(new JLabel("Đơn Vị Tính:"));
    rightPanel.add(new JLabel());

    rightPanel.add(new JLabel("Khối Lượng Cân Hiện Tại:"));
    JLabel weightLabel = new JLabel("0.0", SwingConstants.CENTER);
    weightLabel.setFont(new Font("Arial", Font.BOLD, 24));
    rightPanel.add(weightLabel);

    JTextField weightInput = new JTextField();
    rightPanel.add(weightInput);
    JButton confirmButton = new JButton("XÁC NHẬN");
    rightPanel.add(confirmButton);

    headerPanel.add(rightPanel);

    add(headerPanel, BorderLayout.CENTER);

    // Table panel

    String[] columns = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân", "KL Bi", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet"};
    Object[][] data = {
            {17, "145R12C CA406T 86/84P CASUMINA TL", "BD2024100", "Tổ 1", "Ca 1", "08/10/2024", 100.00, 13.00, 85.00, "Dương Nguyễn Tấn Hòa", 12.00, "BL100"},
            {16, "145R12C CA406T 86/84P CASUMINA TL", "BD2024015", "Tổ 1", "Ca 1", "08/10/2024", 85.00, 11.75, 70.00, "Dương Nguyễn Tấn Hòa", 18.00, "BL015"},
            {15, "145R12C CA406T 86/84P CASUMINA TL", "BD2024014", "Tổ 1", "Ca 1", "08/10/2024", 100.00, 13.75, 85.00, "Dương Nguyễn Tấn Hòa", 32.00, "BL013"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"},
            {14, "145R12C CA406T 86/84P CASUMINA TL", "BD2024013", "Tổ 1", "Ca 1", "08/10/2024", 95.00, 12.25, 80.00, "Dương Nguyễn Tấn Hòa", 24.00, "BL012"}
    };

    // Tạo model cho JTable
    DefaultTableModel model = new DefaultTableModel(data, columns);

    JTable table = new JTable(model);

    // Tùy chỉnh JTable
    table.setRowHeight(30); // Chiều cao mỗi dòng
    table.setFont(new Font("Arial", Font.PLAIN, 14)); // Font chữ
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Font tiêu đề
    table.getTableHeader().setBackground(new Color(255, 153, 51)); // Màu nền tiêu đề
    table.getTableHeader().setForeground(Color.BLACK); // Màu chữ tiêu đề
    table.setGridColor(Color.GRAY); // Màu đường viền
    table.setSelectionBackground(new Color(204, 229, 255)); // Màu nền khi chọn dòng
    table.setSelectionForeground(Color.BLACK); // Màu chữ khi chọn dòng
    table.setPreferredScrollableViewportSize(new Dimension(1200, 400)); // Chiều rộng và chiều cao
    table.setFillsViewportHeight(true); // Tự động mở rộng bảng để lấp đầy Viewport

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


    // Màu nền xen kẽ cho các hàng
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

    // Căn lề dữ liệu (center align)
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    JScrollPane tableScrollPane = new JScrollPane(table);
    add(tableScrollPane, BorderLayout.SOUTH);
  }
}
