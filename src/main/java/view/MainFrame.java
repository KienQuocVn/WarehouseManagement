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
    dashboardPanel.setBackground(new Color(33, 150, 83));
    dashboardPanel.setPreferredSize(new Dimension(getWidth(), 70));

    // Hiển thị "HI! Admin"
    JLabel lblGreeting = new JLabel("HI !", SwingConstants.CENTER);
    lblGreeting.setFont(new Font("Arial", Font.BOLD, 18));
    lblGreeting.setForeground(Color.WHITE);
    lblGreeting.setBounds(20, 10, 100, 30);

    JLabel lblRole = new JLabel("Admin", SwingConstants.CENTER);
    lblRole.setFont(new Font("Arial", Font.PLAIN, 16));
    lblRole.setForeground(Color.WHITE);
    lblRole.setBounds(20, 40, 100, 30);
    dashboardPanel.add(lblGreeting);
    dashboardPanel.add(lblRole);

    // Các nút chức năng với tên và vị trí giữ nguyên
    JButton btnHome = createButton("Trang chủ");
    btnHome.setBounds(150, 30, 90, 20);
    btnHome.addActionListener(e -> showPanel("Home"));

    JButton btnOrder = createButton("Cài đơn");
    btnOrder.setBounds(250, 30, 90, 20);
    btnOrder.addActionListener(e -> showPanel("Order"));

    JButton btnReport = createButton("Thống kê");
    btnReport.setBounds(350, 30, 90, 20);
    btnReport.addActionListener(e -> showPanel("Report"));

    JButton btnImportExport = createButton("Xuất nhập");
    btnImportExport.setBounds(450, 30, 90, 20);
    btnImportExport.addActionListener(e -> showPanel("Import/Export"));

    JButton btnLogin = createButton("Đăng nhập");
    btnLogin.setBounds(550, 30, 90, 20);
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


    JButton btnConnect = createButton("Chưa kết nối");
    btnConnect.setBounds(1280, 30, 100, 20);

    JButton btnSetting = createButton("Cài đặt");
    btnSetting.setBounds(1380, 30, 90, 20);
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
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(new Color(33, 150, 83));
    button.setForeground(Color.WHITE);
    button.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 83)));
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
