package controller;

import Utils.DialogHelper;
import dao.DaoProductionGroup;
import dao.DaoWarehouseStaff;
import model.ProductionGroup;
import model.WarehouseStaff;
import view.CaiDonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarehouseStaffController implements ActionListener {

    private CaiDonPanel caiDonPanel;
    private DaoWarehouseStaff daoWarehouseStaff;


    public WarehouseStaffController(CaiDonPanel caiDonPanel) {
        this.caiDonPanel = caiDonPanel;
        this.daoWarehouseStaff = new DaoWarehouseStaff();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        switch (src) {
            case "Thêm":
                try {
                    handleAddWarehouseStaff();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Làm Mới":
                try {
                    ResetFormWarehouseStaff();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Sửa":
                try {
                    handleUpdateWarehouseStaff();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Xóa":
                try {
                    handleRemoveWarehouseStaff();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Xóa Tất Cả":
                try {
                    handleRemoveAllWarehouseStaff();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }

        }
    }

    private void handleAddWarehouseStaff() {
        try {
            String WarehouseStaffName = caiDonPanel.getTKField().getText().trim(); // Loại bỏ khoảng trắng thừa


            // Kiểm tra các trường nhập liệu
            if (WarehouseStaffName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Thủ Kho!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }


            // Tạo đối tượng sản phẩm và thêm vào DB
            WarehouseStaff warehouseStaff = WarehouseStaff.builder()
                    .staffName(WarehouseStaffName)
                    .build();

            daoWarehouseStaff.insert(warehouseStaff);

            DialogHelper.alert(caiDonPanel, "Thêm Thủ Kho thành công!");
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableDataWarehouseStaff();
            ResetFormWarehouseStaff();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) { // Tùy vào driver SQL, thông báo lỗi có thể khác
                DialogHelper.alert(caiDonPanel, "Tên Thủ Kho đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi thêm Thủ Kho: " + ex.getMessage());
            }

        }
    }

    private void handleUpdateWarehouseStaff() {
        try {
            WarehouseStaff WarehouseStaffCaiDonPanel = caiDonPanel.getWarehouseStaffSendController();
            String WarehouseStaffName = caiDonPanel.getTKField().getText().trim();// Loại bỏ khoảng trắng thừa


            // Kiểm tra các trường nhập liệu
            if (WarehouseStaffName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Thủ Kho !");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }



            // Tạo đối tượng Product và cập nhật
            WarehouseStaff updatedWarehouseStaff = new WarehouseStaff(WarehouseStaffCaiDonPanel.getStaffId(), WarehouseStaffName);

            daoWarehouseStaff.update(updatedWarehouseStaff); // Gọi phương thức update từ DAO
            DialogHelper.alert(caiDonPanel, "Dữ liệu đã được cập nhật." );
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableDataWarehouseStaff();
            ResetFormWarehouseStaff();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) { // Tùy vào driver SQL, thông báo lỗi có thể khác
                DialogHelper.alert(caiDonPanel, "Tên Thủ Kho đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi cập nhật Thủ Kho: " + ex.getMessage());
            }
        }
    }

    private void handleRemoveWarehouseStaff() {
        try {
            // Lấy sản phẩm được chọn từ `caiDonPanel`
            WarehouseStaff WarehouseStaffCaiDonPanel = caiDonPanel.getWarehouseStaffSendController();

            if (WarehouseStaffCaiDonPanel != null) {
                // Hiển thị hộp thoại xác nhận
                boolean confirmed = DialogHelper.confirm(caiDonPanel,
                        "Bạn có chắc muốn xóa Thủ Kho: " + WarehouseStaffCaiDonPanel.getStaffName() + "?");

                // Nếu người dùng chọn "Yes", thực hiện xóa
                if (confirmed) {
                    // Thực hiện xóa sản phẩm (giả sử có phương thức `deleteProduct` trong DAO)
                    daoWarehouseStaff.delete(WarehouseStaffCaiDonPanel.getStaffId());

                    // Hiển thị thông báo và cập nhật lại giao diện
                    DialogHelper.alert(caiDonPanel, "Thủ Kho đã được xóa thành công.");
                    caiDonPanel.updateTableDataWarehouseStaff();
                    ResetFormWarehouseStaff();
                }
            } else {
                // Nếu không có sản phẩm được chọn
                DialogHelper.alert(caiDonPanel, "Thủ Kho nào được chọn để xóa!");
            }
        } catch (Exception ex) {
            // Hiển thị thông báo lỗi
            DialogHelper.alert(caiDonPanel, "Lỗi khi Xóa Thủ Kho: " + ex.getMessage());
        }
    }


    private void handleRemoveAllWarehouseStaff() {
        try {
            // Hiển thị hộp thoại xác nhận
            boolean confirmed = DialogHelper.confirm(caiDonPanel,
                    "Bạn có chắc muốn xóa toàn bộ Thủ Kho không ?: ");

            // Nếu người dùng chọn "Yes", thực hiện xóa
            if (confirmed) {
                // Thực hiện xóa sản phẩm (giả sử có phương thức `deleteProduct` trong DAO)
                daoWarehouseStaff.deleteAll();
                // Hiển thị thông báo và cập nhật lại giao diện
                DialogHelper.alert(caiDonPanel, "Toàn bộ Ca Sản Xuất đã được xóa thành công.");
                caiDonPanel.updateTableDataWarehouseStaff();
                ResetFormWarehouseStaff();
            }

        } catch (Exception ex) {
            // Hiển thị thông báo lỗi
            DialogHelper.alert(caiDonPanel, "Lỗi khi Xóa Ca Sản Xuất: " + ex.getMessage());
        }
    }



    public void ResetFormWarehouseStaff () {
        caiDonPanel.getTKField().setText("");
        caiDonPanel.getbtnThemWarehouseStaff().setEnabled(true);
        caiDonPanel.getbtnSuaWarehouseStaff().setEnabled(false);
        caiDonPanel.getbtnXoaWarehouseStaff().setEnabled(false);
        caiDonPanel.getbtnXoaTatCaWarehouseStaff().setEnabled(true);
        caiDonPanel.updateTableDataWarehouseStaff();
    }


}
