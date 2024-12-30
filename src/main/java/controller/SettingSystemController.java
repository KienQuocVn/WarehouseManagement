package controller;

import dao.DaoSettingSystem;
import dao.DaoShift;
import model.SettingSystem;
import view.CaiDonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import Utils.DialogHelper;
public class SettingSystemController implements ActionListener {
    private CaiDonPanel caiDonPanel;
    private DaoSettingSystem daoSettingSystem;

    public SettingSystemController(CaiDonPanel caiDonPanel) {
        this.caiDonPanel = caiDonPanel;
        this.daoSettingSystem = new DaoSettingSystem();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if ("Lưu".equals(src)) {
            try {
                // Lấy thông tin từ giao diện
                String dirSave = caiDonPanel.getThuMucField().getText();
                String nameFile = caiDonPanel.getTenFileField().getText();
                BigDecimal weightThreshold;
                String printer = (String) caiDonPanel.getMayInComboBox().getSelectedItem();

                if (dirSave.isEmpty() || nameFile.isEmpty()) {
                    DialogHelper.alert(caiDonPanel, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                try {
                    weightThreshold = new BigDecimal(caiDonPanel.getNguongCanField().getText());
                    if (weightThreshold.compareTo(BigDecimal.ZERO) <= 0) {
                        DialogHelper.alert(caiDonPanel, "Ngưỡng cản phải lớn hơn 0!");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    DialogHelper.alert(caiDonPanel, "Vui lòng nhập số hợp lệ cho Ngưỡng cản!");
                    return;
                }



                    // Tạo đối tượng SettingSystem
                SettingSystem setting = SettingSystem.builder()
                        .DirSave(dirSave)
                        .NameFile(nameFile)
                        .WeightThreshold(weightThreshold)
                        .Printer(printer)
                        .build();

                // Gọi Dao để lưu vào cơ sở dữ liệu
                daoSettingSystem.insert(setting);
                loadSettings();
                // Thông báo thành công
                DialogHelper.alert(caiDonPanel, "Lưu thông tin thành công!");
            } catch (Exception ex) {
                DialogHelper.alert(caiDonPanel, "Có lỗi xảy ra khi lưu thông tin: " + ex.getMessage());
            }
        }
    }

    public void loadSettings() {
        DaoSettingSystem dao = new DaoSettingSystem();
        SettingSystem setting = dao.selectLatest(); // Lấy cài đặt mới nhất từ cơ sở dữ liệu

        if (setting != null) {
            // Gán giá trị vào JTextField
            caiDonPanel.getThuMucField().setText(setting.getDirSave());
            caiDonPanel.getTenFileField().setText(setting.getNameFile());
            caiDonPanel.getNguongCanField().setText(setting.getWeightThreshold().toString());

            // Gán giá trị vào JComboBox
            caiDonPanel.getMayInComboBox().setSelectedItem(setting.getPrinter());
        } else {
            // Thiết lập giá trị mặc định nếu không có dữ liệu
            caiDonPanel.getThuMucField().setText("");
            caiDonPanel.getTenFileField().setText("");
            caiDonPanel.getNguongCanField().setText("0.00");
            caiDonPanel.getMayInComboBox().setSelectedIndex(0);
        }
    }

}
