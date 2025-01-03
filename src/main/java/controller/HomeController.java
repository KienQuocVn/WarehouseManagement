package controller;

import dao.*;
import model.Lot;
import model.Pallet;
import model.Product;
import Utils.DialogHelper;
import model.SettingSystem;
import view.HomePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class HomeController implements ActionListener {

    private HomePanel homePanel;
    private DaoProduct daoProduct;
    private DaoSettingSystem daoSettingSystem;
    private DaoShift daoShift;
    private DaoProductionGroup daoProductionGroup;
    private DaoWarehouseStaff daoWarehouseStaff;
    private  DaoLot daoLot;
    private DaoPallet daoPallet;

    public HomeController(HomePanel homePanel) {
        this.homePanel = homePanel;
        this.daoProduct = new DaoProduct();
        this.daoSettingSystem = new DaoSettingSystem();
        this.daoShift = new DaoShift();
        this.daoProductionGroup = new DaoProductionGroup();
        this.daoWarehouseStaff = new DaoWarehouseStaff();
        this.daoLot = new DaoLot();
        this.daoPallet = new DaoPallet();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedItem = (String) homePanel.getMaHangComboBox().getSelectedItem();
        Product product = daoProduct.selectbyName(selectedItem);
        if (product != null) {
            homePanel.getHanSDField().setText(product.getHSD().toString());
            homePanel.getMauField().setText(product.getColor());
            if(product.getColor().equals("Đỏ")) {
                homePanel.getMauField().setBackground(new Color(255, 204, 204));
                homePanel.getHanSDField().setBackground(new Color(255, 204, 204));
            }
            else if(product.getColor().equals("Vàng")) {
                homePanel.getMauField().setBackground(new Color(255, 255, 204));
                homePanel.getHanSDField().setBackground(new Color(255, 255, 204));
            }
             else {
                homePanel.getMauField().setBackground(new Color(204, 255, 204));
                homePanel.getHanSDField().setBackground(new Color(204, 255, 204));
            }
        }
        else {
            homePanel.getHanSDField().setText("0");
            homePanel.getMauField().setText("");
            homePanel.getMauField().setBackground(new Color(255, 255, 204));
            homePanel.getHanSDField().setBackground(new Color(255, 255, 204));
        }

        String src = e.getActionCommand();
        switch (src) {
            case "XÁC NHẬN":
                if (CheckValid()) {
                    try {
                        // Tạo đối tượng Lot
                        Lot lot = new Lot();
                        // Lấy năm hiện tại
                        int currentYear = LocalDate.now().getYear();
                       // Tạo mã số lô (LotIDU)
                        lot.setLotIDU("BD" + currentYear + homePanel.getSoLoField().getText().trim());
                        lot.setProduct(daoProduct.selectbyName((String) homePanel.getMaHangComboBox().getSelectedItem()));
                        lot.setProductionTime(homePanel.getNSXDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        lot.setExpirationDate(LocalDate.now().plusDays((int) Math.floor(Double.parseDouble(homePanel.getHanSDField().getText().trim()))));
                        lot.setWeight(new BigDecimal(homePanel.getKLCLabel().getText().trim())
                                .subtract(new BigDecimal(homePanel.getKlBiField().getText().trim())));
                        lot.setWarehouseWeight(new BigDecimal(homePanel.getKLCLabel().getText().trim()));
                        lot.setWeightDeviation(new BigDecimal(homePanel.getKlBiField().getText().trim()));
                        lot.setShift(daoShift.selectbyName((String) homePanel.getCaSanxuatComboBox().getSelectedItem()));
                        lot.setProductionGroup(daoProductionGroup.selectbyName((String) homePanel.getToSXComboBox().getSelectedItem()));
                        lot.setWarehouseStaff(daoWarehouseStaff.selectbyName((String)  homePanel.getThukhoComboBox().getSelectedItem()));

                        // Thêm Lot và lấy LotID
                        int lotID = daoLot.insertAndGetID(lot);

                        if (lotID > 0) {
                            // Tạo đối tượng Pallet
                            Pallet pallet = new Pallet();
                            pallet.setPalletIDU(homePanel.getSoPalletTe().getText().trim()); //  mã Pallet
                            pallet.setLot(daoLot.selectbyID(lotID)); // Gắn Lot vào Pallet

                            // Thêm Pallet vào cơ sở dữ liệu
                            daoPallet.insert(pallet);

                            // Hiển thị thông báo thành công
                            DialogHelper.alert(homePanel, "Lô hàng và Pallet đã được thêm thành công!");
                            ResetForm();
                        } else {
                            DialogHelper.alert(homePanel, "Không thể thêm lô hàng!");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                        DialogHelper.alert(homePanel, "Lỗi khi thêm dữ liệu: " + ex.getMessage());
                    }
                }
                break;
        }


    }


    public void ResetForm() {
        homePanel.refreshComboBoxData();
        homePanel.refreshprinter();
        homePanel.RefreshT();
        homePanel.refreshToSXComboBoxData();
        homePanel.refreshcaSanxuatComboBoxData();
        homePanel.refreshThukhoComboBoxData();
        homePanel.updateTableLot();
    }


    public boolean CheckValid() {
        // Kiểm tra ComboBox
        if (homePanel.getMaHangComboBox().getSelectedIndex() == 0) {
            DialogHelper.alert(homePanel, "Vui lòng chọn mã hàng hợp lệ.");
            return false;
        }

        if (homePanel.getSoLoField().getText().trim().isEmpty()) {
            DialogHelper.alert(homePanel, "Trường Số Lô không được để trống.");
            return false;
        }

        // Kiểm tra KLCLabel và chuyển về Double
        String KlBiText = homePanel.getKlBiField().getText().trim();
        try {
            double KlBiValue = Double.parseDouble(KlBiText);
            if (KlBiValue <= 0) {
                DialogHelper.alert(homePanel, "Khối lượng Bì phải lớn hơn 0.");
                return false;
            }
        } catch (NumberFormatException ex) {
            DialogHelper.alert(homePanel, "Khối lượng Bì phải là một số hợp lệ.");
            return false;
        }


        if (homePanel.getToSXComboBox().getSelectedIndex() == 0) {
            DialogHelper.alert(homePanel, "Vui lòng chọn tổ sản xuất hợp lệ.");
            return false;
        }
        if (homePanel.getCaSanxuatComboBox().getSelectedIndex() == 0) {
            DialogHelper.alert(homePanel, "Vui lòng chọn ca sản xuất hợp lệ.");
            return false;
        }
        if (homePanel.getThukhoComboBox().getSelectedIndex() == 0) {
            DialogHelper.alert(homePanel, "Vui lòng chọn thủ kho hợp lệ.");
            return false;
        }

        // Chuyển đổi java.util.Date sang java.time.LocalDate
        Date date = homePanel.getNSXDate().getDate(); // Lấy ngày từ JDateChooser
        if (date != null) {
            LocalDate localDate = homePanel.getNSXDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        } else {
            DialogHelper.alert(homePanel, "Vui lòng chọn ngày sản xuất hợp lệ.");
            return false;
        }

        // Kiểm tra JTextField
        if (homePanel.getMauField().getText().trim().isEmpty()) {
            DialogHelper.alert(homePanel, "Trường màu không được để trống.");
            return false;
        }
        if (homePanel.getHanSDField().getText().trim().isEmpty()) {
            DialogHelper.alert(homePanel, "Trường hạn sử dụng không được để trống.");
            return false;
        }
        if (homePanel.getSoPalletTe().getText().trim().isEmpty()) {
            DialogHelper.alert(homePanel, "Trường số pallet không được để trống.");
            return false;
        }
        if (homePanel.getKLTField().getText().trim().isEmpty()) {
            DialogHelper.alert(homePanel, "Trường khối lượng tịnh không được để trống.");
            return false;
        }

        // Kiểm tra KLCLabel và chuyển về Double
        String klcText = homePanel.getKLCLabel().getText().trim();
        try {
            BigDecimal klcValue = new BigDecimal(klcText); // Chuyển đổi về BigDecimal

            // Kiểm tra khối lượng cân lớn hơn 0
            if (klcValue.compareTo(BigDecimal.ZERO) <= 0) {
                DialogHelper.alert(homePanel, "Khối lượng cân phải lớn hơn 0.");
                return false;
            }

            // Kiểm tra khối lượng cân với ngưỡng
            SettingSystem settingSystem = daoSettingSystem.selectLatest();
            if (settingSystem != null) {
                BigDecimal weightThreshold = settingSystem.getWeightThreshold(); // Lấy ngưỡng cân từ hệ thống
                if (klcValue.compareTo(weightThreshold) < 0) {
                    DialogHelper.alert(homePanel, "Khối lượng cân phải lớn hơn hoặc bằng ngưỡng: " + weightThreshold);
                    return false;
                }
            } else {
                DialogHelper.alert(homePanel, "Hệ Thống chưa được cấu hình!");
                return false;
            }

        } catch (NumberFormatException | NullPointerException ex) {
            DialogHelper.alert(homePanel, "Khối lượng cân phải là một số hợp lệ.");
            return false;
        }

        // Tất cả hợp lệ
        return true;
    }


}
