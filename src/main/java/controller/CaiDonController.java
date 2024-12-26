package controller;

import Utils.DialogHelper;
import dao.DaoProduct;
import model.Product;
import view.CaiDonPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaiDonController implements ActionListener {

    private CaiDonPanel caiDonPanel;
    private DaoProduct daoProduct;

    public CaiDonController(CaiDonPanel caiDonPanel) {
        this.caiDonPanel = caiDonPanel;
        this.daoProduct = new DaoProduct();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        switch (src) {
            case "Thêm":
                try {
                    handleAddProduct();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            case "Làm Mới":
                try {
                    ResetFormMaHang();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
        }
    }


    private void handleAddProduct() {
        try {
            String productName = caiDonPanel.getDHField().getText().trim(); // Loại bỏ khoảng trắng thừa
            String hsdText = caiDonPanel.getHSDField().getText().trim(); // Lấy giá trị HSD dưới dạng chuỗi
            String color = (String) caiDonPanel.getThukhoComboBox().getSelectedItem();

            // Kiểm tra các trường nhập liệu
            if (productName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Tên Đơn Hàng!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }

            if (hsdText.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Hạn Sử Dụng!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }

            if (color == null || color.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng chọn Màu!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }

            // Chuyển đổi giá trị Hạn Sử Dụng sang kiểu double và kiểm tra tính hợp lệ
            double hsd = 0;
            try {
                hsd = Double.parseDouble(hsdText); // Chuyển đổi chuỗi thành double
            } catch (NumberFormatException ex) {
                DialogHelper.alert(caiDonPanel, "Hạn Sử Dụng phải là một số hợp lệ!");
                return; // Dừng hàm nếu giá trị HSD không hợp lệ
            }

            // In ra thông tin để kiểm tra



            // Tạo đối tượng sản phẩm và thêm vào DB
            Product product = Product.builder()
                    .productName(productName)
                    .HSD(hsd) // Sử dụng giá trị double
                    .color(color)
                    .build();


            daoProduct.insert(product);

            DialogHelper.alert(caiDonPanel, "Thêm Hàng thành công!");
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableData();
            ResetFormMaHang();
        } catch (Exception ex) {
            DialogHelper.alert(caiDonPanel, "Lỗi khi thêm Hàng: " + ex.getMessage());
        }
    }

    public void ResetFormMaHang () {
        caiDonPanel.getDHField().setText("");
        caiDonPanel.getHSDField().setText("");
        caiDonPanel.getThukhoComboBox().setSelectedIndex(0);

    }




}
