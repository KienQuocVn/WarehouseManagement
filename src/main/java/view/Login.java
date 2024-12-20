package view;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;


public class Login extends JPanel {

  Color panDefualt, panEnter, panClick;

  public Login() {
    setBackground(new Color(13, 39, 51));
    setLayout(null);

    JLabel jLabel1 = new JLabel("LOGIN");
    jLabel1.setFont(new Font("Cantarell", Font.BOLD, 48));
    jLabel1.setForeground(Color.WHITE);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setBounds(200, 50, 300, 50);
    add(jLabel1);

    JLabel jLabel4 = new JLabel("Username");
    jLabel4.setFont(new Font("SF Pro Display", Font.BOLD, 18));
    jLabel4.setForeground(Color.WHITE);
    jLabel4.setBounds(200, 130, 300, 20);
    add(jLabel4);

    JTextField loginUser = new JTextField();
    loginUser.setBackground(new Color(13, 39, 51));
    loginUser.setForeground(Color.WHITE);
    loginUser.setFont(new Font("SF Pro Display", Font.BOLD, 18));
    loginUser.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    loginUser.setBounds(200, 160, 300, 40);
    add(loginUser);

    JLabel jLabel8 = new JLabel("Password");
    jLabel8.setFont(new Font("SF Pro Display", Font.BOLD, 18));
    jLabel8.setForeground(Color.WHITE);
    jLabel8.setBounds(200, 220, 300, 20);
    add(jLabel8);

    JPasswordField passwordUser = new JPasswordField();
    passwordUser.setBackground(new Color(13, 39, 51));
    passwordUser.setForeground(Color.WHITE);
    passwordUser.setFont(new Font("SF Pro Display", Font.BOLD, 18));
    passwordUser.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    passwordUser.setBounds(200, 250, 300, 40);
    add(passwordUser);

    JButton btnLogin = new JButton("Đăng nhập");
    btnLogin.setBackground(new Color(89, 168, 105));
    btnLogin.setForeground(Color.WHITE);
    btnLogin.setFont(new Font("SF Pro Display", Font.BOLD, 18));
    btnLogin.setBounds(250, 320, 200, 40);
    btnLogin.addActionListener(e -> JOptionPane.showMessageDialog(this, "Đăng nhập thành công!"));
    add(btnLogin);

    JLabel lblForgotPassword = new JLabel("Quên mật khẩu?");
    lblForgotPassword.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
    lblForgotPassword.setForeground(Color.WHITE);
    lblForgotPassword.setBounds(270, 380, 150, 20);
    add(lblForgotPassword);
  }
}