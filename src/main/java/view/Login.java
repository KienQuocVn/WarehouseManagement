package view;
import javax.swing.*;
import java.awt.Color;
import javax.swing.UIManager;


public class Login extends JFrame {

  Color panDefualt, panEnter, panClick;

  public Login() {
    initComponents();
    setLocationRelativeTo(null);
    UIManager.put("Button.focus", Color.white);
    panDefualt = new Color(89, 168, 105);
    panClick = new Color(89, 168, 120);
    panEnter = new Color(89, 168, 120);
    ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource("img/logo.png"));
    setIconImage(logo.getImage());
  }


  private void initComponents() {
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JTextField loginUser = new JTextField();
    JLabel jLabel8 = new JLabel();
    JPasswordField passwordUser = new JPasswordField();
    JPanel JPaneLogin = new JPanel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel7 = new JLabel();
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Đăng nhập vào phần mềm");
    setResizable(false);

    // jPanel1
    jPanel1.setBackground(new java.awt.Color(13, 39, 51));

    jLabel1.setFont(new java.awt.Font("Cantarell", 1, 48));
    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("LOGIN");

    jLabel4.setFont(new java.awt.Font("SF Pro Display", 1, 18));
    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setText("Username");

    loginUser.setBackground(new java.awt.Color(13, 39, 51));
    loginUser.setFont(new java.awt.Font("SF Pro Display", 1, 18));
    loginUser.setForeground(new java.awt.Color(255, 255, 255));
    loginUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

    jLabel8.setFont(new java.awt.Font("SF Pro Display", 1, 18));
    jLabel8.setForeground(new java.awt.Color(255, 255, 255));
    jLabel8.setText("Password");

    passwordUser.setBackground(new java.awt.Color(13, 39, 51));
    passwordUser.setFont(new java.awt.Font("SF Pro Display", 1, 18));
    passwordUser.setForeground(new java.awt.Color(255, 255, 255));
    passwordUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

    // Panel đăng nhập
    JPaneLogin.setBackground(new java.awt.Color(89, 168, 105));
    JPaneLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jLabel2.setFont(new java.awt.Font("SF Pro Display", 1, 18));
    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("Đăng nhập");

    javax.swing.GroupLayout JPaneLoginLayout = new javax.swing.GroupLayout(JPaneLogin);
    JPaneLogin.setLayout(JPaneLoginLayout);
    JPaneLoginLayout.setHorizontalGroup(
        JPaneLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPaneLoginLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addContainerGap(40, Short.MAX_VALUE))
    );
    JPaneLoginLayout.setVerticalGroup(
        JPaneLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
    );

    // Quên mật khẩu
    jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 14));
    jLabel7.setForeground(new java.awt.Color(255, 255, 255));
    jLabel7.setText("Quên mật khẩu?");
    jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // GroupLayout của jPanel1
    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(loginUser)
                            .addComponent(jLabel4)
                            .addComponent(passwordUser, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JPaneLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(jLabel1)))
                .addContainerGap(200, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(JPaneLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addContainerGap(50, Short.MAX_VALUE))
    );

    getContentPane().add(jPanel1);

    pack();
    setLocationRelativeTo(null);
  }

  public static void main(String[] args) {
    new Login().setVisible(true);
  }
}