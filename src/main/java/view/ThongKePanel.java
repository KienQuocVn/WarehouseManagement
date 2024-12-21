package view;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class ThongKePanel extends JPanel {

  public ThongKePanel() {
    setLayout(new BorderLayout());

    // Header panel
    JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Tiêu đề
    JLabel titleLabel = new JLabel("KẾT QUẢ CẦN BÁN THÀNH PHẨM", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

    // Panel chứa bộ lọc
    JPanel filterPanel = new JPanel(new GridLayout(1, 11, 2, 2));

    // Tạo ô cột với JLabel và thành phần nhập liệu
    filterPanel.add(createColumn("Tổ Sản Xuất:", new JComboBox<>(new String[]{"Tất cả", "Tổ 1", "Tổ 2"})));
    filterPanel.add(createColumn("Ca Sản Xuất:", new JComboBox<>(new String[]{"Tất cả", "Ca 1", "Ca 2"})));
    filterPanel.add(createColumn("Xuất/Nhập:", new JComboBox<>(new String[]{"Xuất", "Nhập"})));
    filterPanel.add(createColumn("Mã Hàng:", new JComboBox<>(new String[]{"Tất cả", "MH01", "MH02"})));

    // Sử dụng JDateChooser cho Từ Ngày và Đến Ngày
    JDateChooser fromDateChooser = new JDateChooser();
    fromDateChooser.setDate(new Date());
    fromDateChooser.setDateFormatString("dd/MM/yyyy");

    JDateChooser toDateChooser = new JDateChooser();
    toDateChooser.setDate(new Date());
    toDateChooser.setDateFormatString("dd/MM/yyyy");

    filterPanel.add(createColumn("Từ Ngày:", fromDateChooser));
    filterPanel.add(createColumn("Đến Ngày:", toDateChooser));
    filterPanel.add(createColumnBlue("Xóa Chọn:", new JButton(resizeIcon("/img/reset.png", 25, 25))));
    filterPanel.add(createColumnBlue("Tìm Kiếm:", new JButton(resizeIcon("/img/search.png", 25, 25))));
    filterPanel.add(createColumn("Loại Report:", new JComboBox<>(new String[]{"Loại 1", "Loại 2"})));
    filterPanel.add(createColumnBlue("Xem Trước:", new JButton(resizeIcon("/img/printer.png", 25, 25))));
    filterPanel.add(createColumnBlue("Excel:",new JButton(resizeIcon("/img/document.png", 25, 25))));


    headerPanel.add(titleLabel, BorderLayout.NORTH);
    headerPanel.add(filterPanel, BorderLayout.CENTER);
    add(headerPanel, BorderLayout.NORTH);
    add(headerPanel, BorderLayout.NORTH);

    // Table
    String[] columnNames = {"Số Phiếu", "Mã Hàng", "Số Lô", "Tổ", "Ca", "Thời Gian SX", "KL Cân", "KL Bì", "KL Hàng", "Thủ Kho", "HSD", "Số Pallet", "Xuất/Nhập"};
    Object[][] data = {
        {"6", "Đơn 2", "0408N", "Tổ 1", "Ca 1", "12:00", "12.0", "0.0", "12.0", "HỦY", "26/05/2024", "4.0", "HỦY"},
        {"6", "Đơn 2", "0408N", "Tổ 1", "Ca 1", "12:00", "12.0", "0.0", "12.0", "HỦY", "26/05/2024", "4.0", "HỦY"}
    };

    DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Customizing table
    table.setRowHeight(25);
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
    table.getTableHeader().setBackground(Color.LIGHT_GRAY);
    table.setFont(new Font("Arial", Font.PLAIN, 12));

    add(scrollPane, BorderLayout.CENTER);


    // Footer Panel
    JPanel footerPanel = new JPanel(new BorderLayout()); // Sử dụng BorderLayout

    // Sử dụng GridBagLayout cho summaryPanel
    JPanel summaryPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 2, 2, 2); // Khoảng cách giữa các ô
    gbc.fill = GridBagConstraints.HORIZONTAL; // Giãn ngang
    gbc.weightx = 1.0; // Giãn toàn bộ chiều ngang
    gbc.weighty = 0;   // Không cần giãn chiều dọc

    // ====== HÀNG 1 ======
    gbc.gridx = 0; // Cột đầu tiên
    gbc.gridy = 0; // Hàng đầu tiên
    gbc.gridwidth = 1; // Chiếm 1 cột
    summaryPanel.add(createColumnYellow("Số Phiếu", new JTextField()), gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    summaryPanel.add(createColumn("Khối Lượng Bì", new JTextField()), gbc);

    gbc.gridx = 2;
    summaryPanel.add(createColumnYellow("Khối Lượng Tổng", new JTextField()), gbc);

    gbc.gridx = 3;
    summaryPanel.add(createColumn("Khối Lượng Tịnh", new JTextField()), gbc);

    gbc.gridx = 4;
    summaryPanel.add(createColumnYellow("Ngày Sản Xuất", new JTextField()), gbc);

    gbc.gridx = 5;
    summaryPanel.add(createColumn("Thủ Kho", new JComboBox<>(new String[]{"Tất cả", "Ca 1", "Ca 2"})), gbc);

    gbc.gridx = 6;
    summaryPanel.add(createColumnBlue("Lưu Thay Đổi",new JButton(resizeIcon("/img/diskette.png", 25, 25))),gbc);

    gbc.gridx = 7;
    summaryPanel.add(createColumnRed("Xóa Dữ Liệu", new JButton(resizeIcon("/img/trash-bin.png", 25, 25))),gbc);

    // ====== HÀNG 2 ======
    gbc.gridx = 0; // Cột đầu tiên
    gbc.gridy = 1; // Hàng thứ hai
    gbc.gridwidth = 2; // Chiếm 2 cột
    summaryPanel.add(createColumn("Tên Đơn Hàng", new JComboBox<>(new String[]{"Tất cả", "Tổ 1", "Tổ 2"})), gbc);

    gbc.gridx = 2; // Cột thứ ba
    gbc.gridwidth = 1; // Trở lại chiếm 1 cột
    summaryPanel.add(createColumn("Số Lô", new JTextField()), gbc);

    gbc.gridx = 3; // Cột thứ tư
    summaryPanel.add(createColumn("Hạn Sử Dụng(Ngày)", new JTextField()), gbc);

    gbc.gridx = 4; // Cột thứ năm
    summaryPanel.add(createColumn("Tổ SX", new JComboBox<>(new String[]{"Tất cả", "MH01", "MH02"})), gbc);

    gbc.gridx = 5; // Cột thứ sáu
    summaryPanel.add(createColumn("Ca Sản Xuất", new JComboBox<>(new String[]{"Tất cả", "Tổ 1", "Tổ 2"})), gbc);

    gbc.gridx = 6; // Cột thứ bảy
    summaryPanel.add(createColumnBlue("In Phiếu",  new JButton(resizeIcon("/img/printer.png", 25, 25))),gbc);

    gbc.gridx = 7; // Cột thứ tám
    summaryPanel.add(createColumnRed("Xóa Tất Cả",new JButton(resizeIcon("/img/trash-bin.png", 25, 25))),gbc);

    footerPanel.add(summaryPanel, BorderLayout.CENTER);

    add(footerPanel, BorderLayout.SOUTH);

  }
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
