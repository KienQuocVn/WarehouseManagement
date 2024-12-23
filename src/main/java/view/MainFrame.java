package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private JPanel dashboardPanel;
  private JPanel contentPanel;
  private CardLayout cardLayout;

  public MainFrame() {
    setTitle("Phần Mềm Cân Xuất - Nhập Kho");
    setSize(1500, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);
    initDashboard();
    initContentPanel();
    setVisible(true);
  }

  private void initDashboard() {
    dashboardPanel = new JPanel();
    dashboardPanel.setLayout(null);
    dashboardPanel.setBackground(Color.WHITE);
    dashboardPanel.setPreferredSize(new Dimension(getWidth(), 50));

    // Các nút chức năng với tên và vị trí giữ nguyên
    JButton btnHome = createButton("Trang chủ");
    btnHome.setBounds(50, 10, 90, 30);
    btnHome.addActionListener(e -> showPanel("Home"));

    JButton btnOrder = createButton("Cài đơn");
    btnOrder.setBounds(150, 10, 90, 30);
    btnOrder.addActionListener(e -> showPanel("Order"));

    JButton btnReport = createButton("Thống kê");
    btnReport.setBounds(250, 10, 90, 30);
    btnReport.addActionListener(e -> showPanel("Report"));

    JButton btnImportExport = createButton("Xuất nhập");
    btnImportExport.setBounds(350, 10, 90, 30);
    btnImportExport.addActionListener(e -> showPanel("Import/Export"));

    JButton btnLogin = createButton("Đăng nhập");
    btnLogin.setBounds(450, 10, 90, 30);
    btnLogin.addActionListener(e -> {
      this.setVisible(false); // Ẩn MainFrame
      JFrame loginFrame = new JFrame("Đăng nhập");
      loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      loginFrame.setSize(600, 500);
      loginFrame.add(new Login());
      loginFrame.setLocationRelativeTo(null);
      loginFrame.setVisible(true);

      // Khi login thành công, hiển thị lại MainFrame
      loginFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
          MainFrame.this.setVisible(true);
        }
      });
    });


    JButton btnConnect = new JButton("Chưa kết nối");
    btnConnect.setBackground(Color.RED);
    btnConnect.setFont(new Font("Arial", Font.BOLD, 15));
    btnConnect.setBounds(1230, 10, 110, 30);
    btnConnect.setForeground(Color.BLACK);
    btnConnect.setBorder(BorderFactory.createLineBorder(Color.RED));
    btnConnect.setFocusPainted(false);

    JButton btnSetting = createButton("Cài đặt");
    btnSetting.setBounds(1350, 10, 90, 30);
    btnSetting.addActionListener(e -> System.exit(0));

    dashboardPanel.add(btnHome);
    dashboardPanel.add(btnOrder);
    dashboardPanel.add(btnImportExport);
    dashboardPanel.add(btnLogin);
    dashboardPanel.add(btnReport);
    dashboardPanel.add(btnConnect);
    dashboardPanel.add(btnSetting);

    add(dashboardPanel, BorderLayout.SOUTH);
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 15));
    button.setBackground(new Color(70, 130, 180)); // Đặt màu nền nút
    button.setForeground(Color.BLACK); // Đặt màu chữ
    button.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180))); // Viền cùng màu nền
    button.setFocusPainted(false);
    return button;
  }


  private void initContentPanel() {
    cardLayout = new CardLayout();
    contentPanel = new JPanel(cardLayout);

    contentPanel.add(new HomePanel(), "Home");
    contentPanel.add(new CaiDonPanel(), "Order");
    contentPanel.add(new ThongKePanel(), "Report");
    contentPanel.add(new XuatNhapPanel(), "Import/Export");
    contentPanel.add(new Login(), "Login");


    add(contentPanel, BorderLayout.CENTER);

    showPanel("Home");
  }

  private void showPanel(String name) {
    cardLayout.show(contentPanel, name);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(MainFrame::new);
  }
}
