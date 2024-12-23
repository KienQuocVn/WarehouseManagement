package view;

import javax.swing.*;
import java.awt.CardLayout;
import javax.swing.JOptionPane;

public class RecoverPassword extends JDialog {

  private String otpNumber;
  private JTextField txtEmail;
  private JPanel jPanel3;
  private JTextField txtOtp;

  public RecoverPassword(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    setLocationRelativeTo(null);

  }


  private void initComponents() {

    JPanel jPanel2 = new JPanel();
    JLabel jLabel1 = new JLabel();
    jPanel3 = new JPanel();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel2 = new JLabel();
    txtEmail = new JTextField();
    JButton jButton1 = new JButton();
    JPanel jPanel5 = new JPanel();
    JLabel jLabel3 = new JLabel();
    txtOtp = new JTextField();
    JButton jButton2 = new JButton();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel4 = new JPanel();
    JLabel jLabel5 = new JLabel();
    JPasswordField txtPassword = new JPasswordField();
    JButton jButton3 = new JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Khôi phục mật khẩu");
    setResizable(false);

    jPanel2.setBackground(new java.awt.Color(89, 168, 105));
    jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 24));
    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
    jLabel1.setText("KHÔI PHỤC MẬT KHẨU");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
    );

    jPanel3.setLayout(new java.awt.CardLayout());

    jPanel1.setBackground(new java.awt.Color(255, 255, 255));

    jLabel2.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
    jLabel2.setText("Nhập địa chỉ email tài khoản");

    jButton1.setBackground(new java.awt.Color(255, 255, 255));
    jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(189, 189, 189, 255), 2, true));
    jButton1.setText("Gửi mã xác nhận");
    jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 43, Short.MAX_VALUE))
    );

    jPanel3.add(jPanel1, "card2");

    jPanel5.setBackground(new java.awt.Color(255, 255, 255));

    jLabel3.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
    jLabel3.setText("Mã xác nhận gồm 6 chữ số đã được gửi vào địa chỉ email của bạn");

    jButton2.setText("Xác nhận");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });

    jLabel4.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
    jLabel4.setText("Nhập mã xác nhận:");

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
    );

    jPanel3.add(jPanel5, "card3");

    jPanel4.setBackground(new java.awt.Color(255, 255, 255));

    jLabel5.setFont(new java.awt.Font("SF Pro Display", 0, 16)); // NOI18N
    jLabel5.setText("Nhập mật khẩu mới");

    jButton3.setText("Đổi mật khẩu");


    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(txtPassword))
                .addContainerGap(42, Short.MAX_VALUE))
    );

    jPanel3.add(jPanel4, "card4");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    String email = txtEmail.getText();
    if (email.equals("")) {
      JOptionPane.showMessageDialog(this, "Email không được để trống !");
    } else {
      if (isValid(email)) {
        if (checkEmail(email)) {
          otpNumber = getOTP();
          CardLayout forgotPassword = (CardLayout) jPanel3.getLayout();
          forgotPassword.next(jPanel3);
        } else {
          JOptionPane.showMessageDialog(this, "Email không tồn tại trên hệ thống !");
        }
      } else {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email !");
      }
    }
  }//GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
    String otp = txtOtp.getText();
    if (otp.equals("")) {
      JOptionPane.showMessageDialog(this, "Không được để trống !");
    } else {
      if (otp.length() < 6 || otp.length() > 6) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng 6 chữ số !");
      } else {
        if (otp.equals(otpNumber)) {
          CardLayout forgotPassword = (CardLayout) jPanel3.getLayout();
          forgotPassword.next(jPanel3);
        } else {
          JOptionPane.showMessageDialog(this, "Mã xác nhận không chính xác !");
        }
      }
    }
  }

  static boolean isValid(String email) {
    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    return email.matches(regex);
  }

  private boolean checkEmail(String email) {

    return false;
  }

  private String getOTP() {
    int min = 100000;
    int max = 999999;
    return Integer.toString((int) ((Math.random() * (max - min)) + min));
  }
}