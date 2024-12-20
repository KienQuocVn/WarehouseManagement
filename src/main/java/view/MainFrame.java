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
    // logo = new ImageIcon(ClassLoader.getSystemResource("img/logo.png"));
    //setIconImage(logo.getImage());
    initDashboard();
    initContentPanel();
    setVisible(true);
  }

  private void initDashboard() {
    dashboardPanel = new JPanel();
    dashboardPanel.setLayout(null); // Sử dụng layout null để custom vị trí
    dashboardPanel.setBackground(new Color(33, 150, 83));
    dashboardPanel.setPreferredSize(new Dimension(220, getHeight()));

    // Hiển thị "HI! Admin"
    JLabel lblGreeting = new JLabel("HI !", SwingConstants.CENTER);
    lblGreeting.setFont(new Font("Arial", Font.BOLD, 18));
    lblGreeting.setForeground(Color.WHITE);
    lblGreeting.setBounds(0, 20, 220, 30);

    JLabel lblRole = new JLabel("Admin", SwingConstants.CENTER);
    lblRole.setFont(new Font("Arial", Font.PLAIN, 16));
    lblRole.setForeground(Color.WHITE);
    lblRole.setBounds(0, 50, 220, 30);
    dashboardPanel.add(lblGreeting);
    dashboardPanel.add(lblRole);



    // Các nút chức năng với tên và vị trí giữ nguyên
    JButton btnHome = createButton("Thống kê");
    btnHome.setBounds(10, 100, 200, 40);
    btnHome.addActionListener(e -> showPanel("Home"));

    JButton btnInputWeight = createButton("Nhập mẻ cân");
    btnInputWeight.setBounds(10, 150, 200, 40);
    btnInputWeight.addActionListener(e -> showPanel("InputWeight"));

    JButton btnProductSpec = createButton("Quản lý quy cách");
    btnProductSpec.setBounds(10, 200, 200, 40);
    btnProductSpec.addActionListener(e -> showPanel("ProductSpec"));

    JButton btnHistory = createButton("Lịch sử cân");
    btnHistory.setBounds(10, 250, 200, 40);
    btnHistory.addActionListener(e -> showPanel("History"));

    JButton btnWarehouse = createButton("Quản lý kho");
    btnWarehouse.setBounds(10, 300, 200, 40);
    btnWarehouse.addActionListener(e -> showPanel("Warehouse"));

    JButton btnReport = createButton("Xuất báo cáo");
    btnReport.setBounds(10, 350, 200, 40);
    btnReport.addActionListener(e -> showPanel("Report"));

    JButton btnChangeInfo = createButton("Đổi thông tin");
    btnChangeInfo.setBounds(10, getHeight() - 120, 200, 40);

    JButton btnLogout = createButton("Đăng xuất");
    btnLogout.setBounds(10, getHeight() - 70, 200, 40);
    btnLogout.addActionListener(e -> System.exit(0));

    dashboardPanel.add(btnHome);
    dashboardPanel.add(btnInputWeight);
    dashboardPanel.add(btnProductSpec);
    dashboardPanel.add(btnHistory);
    dashboardPanel.add(btnWarehouse);
    dashboardPanel.add(btnReport);
    dashboardPanel.add(btnChangeInfo);
    dashboardPanel.add(btnLogout);

    add(dashboardPanel, BorderLayout.WEST);
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
    contentPanel.add(new InputWeightPanel(), "InputWeight");
    contentPanel.add(new ProductSpecPanel(), "ProductSpec");
    contentPanel.add(new HistoryPanel(), "History");
    contentPanel.add(new WarehousePanel(), "Warehouse");
    contentPanel.add(new ReportPanel(), "Report");

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
