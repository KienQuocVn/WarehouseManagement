package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomePanel extends JPanel {

  public HomePanel() {
    setLayout(new BorderLayout());

    // Header panel
    JPanel headerPanel = new JPanel(new GridLayout(1, 3));

    // Left panel: Thông Tin Mã Hàng
    JPanel leftPanel = new JPanel(new GridLayout(7, 2, 5, 5));
    leftPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN MÃ HÀNG"));

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
    centerPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN SẢN XUẤT"));

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
    rightPanel.setBorder(BorderFactory.createTitledBorder("KHỐI LƯỢNG CÂN"));

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

    add(headerPanel, BorderLayout.NORTH);

    String[] columns = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân", "KL Bi", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet"};
    Object[][] data = {
        {"6", "Đơn 2", "0408N", "Me01", "CA", "12.0", "0.0", "12.0", "-12.0", "HUY", "4.0", ""},
        {"3", "Đơn 2", "0408N", "Me01", "CA", "12.0", "0.0", "12.0", "-12.0", "HUY", "4.0", ""}
    };

    JTable table = new JTable(new DefaultTableModel(data, columns));
    JScrollPane tableScrollPane = new JScrollPane(table);
    add(tableScrollPane, BorderLayout.CENTER);
  }
}
