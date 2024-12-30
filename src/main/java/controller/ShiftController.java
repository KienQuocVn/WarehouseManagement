package controller;


import dao.DaoShift;
import model.Product;
import model.Shift;
import view.CaiDonPanel;
import Utils.DialogHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShiftController implements ActionListener {
    private CaiDonPanel caiDonPanel;
    private DaoShift daoShift;


    public ShiftController(CaiDonPanel caiDonPanel) {
        this.caiDonPanel = caiDonPanel;
        this.daoShift = new DaoShift();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        switch (src) {
            case "Thêm":
                try {
                    handleAddShift();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Làm Mới":
                try {
                    ResetFormShift();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Sửa":
                try {
                    handleUpdateShift();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Xóa":
                try {
                    handleRemoveShift();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Xóa Tất Cả":
                try {
                    handleRemoveAllShift();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }

        }

    }

    private void handleAddShift() {
        try {
            String ShiftName = caiDonPanel.getCSXShiftField().getText().trim(); // Loại bỏ khoảng trắng thừa


            // Kiểm tra các trường nhập liệu
            if (ShiftName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Ca Sản Xuất!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }


            // Tạo đối tượng sản phẩm và thêm vào DB
            Shift shift = Shift.builder()
                    .shiftName(ShiftName)
                    .build();

            daoShift.insert(shift);

            DialogHelper.alert(caiDonPanel, "Thêm Ca Sản Xuất thành công!");
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableDataShift();
            ResetFormShift();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) { // Tùy vào driver SQL, thông báo lỗi có thể khác
                DialogHelper.alert(caiDonPanel, "Tên Ca Sản Xuất đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi thêm Ca Sản Xuất: " + ex.getMessage());
            }

        }
    }

    private void handleUpdateShift() {
        try {
            Shift ShiftCaiDonPanel = caiDonPanel.getShiftSendControllerg();
            String ShiftName = caiDonPanel.getCSXShiftField().getText().trim(); // Loại bỏ khoảng trắng thừa


            // Kiểm tra các trường nhập liệu
            if (ShiftName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Tên Ca Sản Xuất !");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }



            // Tạo đối tượng Product và cập nhật
            Shift updatedShift = new Shift(ShiftCaiDonPanel.getShiftId(), ShiftName);

            daoShift.update(updatedShift); // Gọi phương thức update từ DAO
            DialogHelper.alert(caiDonPanel, "Dữ liệu đã được cập nhật." );
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableDataShift();
            ResetFormShift();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) { // Tùy vào driver SQL, thông báo lỗi có thể khác
                DialogHelper.alert(caiDonPanel, "Tên Ca Sản Xuất đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi cập nhật Ca Sản Xuất: " + ex.getMessage());
            }
        }
    }

    private void handleRemoveShift() {
        try {
            // Lấy sản phẩm được chọn từ `caiDonPanel`
            Shift ShiftCaiDonPanel = caiDonPanel.getShiftSendControllerg();

            if (ShiftCaiDonPanel != null) {
                // Hiển thị hộp thoại xác nhận
                boolean confirmed = DialogHelper.confirm(caiDonPanel,
                        "Bạn có chắc muốn xóa Ca Sản Xuất: " + ShiftCaiDonPanel.getShiftName() + "?");

                // Nếu người dùng chọn "Yes", thực hiện xóa
                if (confirmed) {
                    // Thực hiện xóa sản phẩm (giả sử có phương thức `deleteProduct` trong DAO)
                    daoShift.delete(ShiftCaiDonPanel.getShiftId());

                    // Hiển thị thông báo và cập nhật lại giao diện
                    DialogHelper.alert(caiDonPanel, "Ca Sản Xuất đã được xóa thành công.");
                    caiDonPanel.updateTableDataShift();
                    ResetFormShift();
                }
            } else {
                // Nếu không có sản phẩm được chọn
                DialogHelper.alert(caiDonPanel, "Ca Sản Xuất nào được chọn để xóa!");
            }
        } catch (Exception ex) {
            // Hiển thị thông báo lỗi
            DialogHelper.alert(caiDonPanel, "Lỗi khi Xóa Ca Sản Xuất: " + ex.getMessage());
        }
    }


    private void handleRemoveAllShift() {
        try {
            // Hiển thị hộp thoại xác nhận
            boolean confirmed = DialogHelper.confirm(caiDonPanel,
                    "Bạn có chắc muốn xóa toàn bộ Ca Sản Xuất không ?: ");

            // Nếu người dùng chọn "Yes", thực hiện xóa
            if (confirmed) {
                // Thực hiện xóa sản phẩm (giả sử có phương thức `deleteProduct` trong DAO)
                daoShift.deleteAll();
                // Hiển thị thông báo và cập nhật lại giao diện
                DialogHelper.alert(caiDonPanel, "Toàn bộ Ca Sản Xuất đã được xóa thành công.");
                caiDonPanel.updateTableDataShift();
                ResetFormShift();
            }

        } catch (Exception ex) {
            // Hiển thị thông báo lỗi
            DialogHelper.alert(caiDonPanel, "Lỗi khi Xóa Ca Sản Xuất: " + ex.getMessage());
        }
    }



    public void ResetFormShift () {
        caiDonPanel.getCSXShiftField().setText("");
        caiDonPanel.getbtnThemShift().setEnabled(true);
        caiDonPanel.getbtnSuaShift().setEnabled(false);
        caiDonPanel.getbtnXoaShift().setEnabled(false);
        caiDonPanel.getbtnXoaTatCaShift().setEnabled(true);
        caiDonPanel.updateTableDataShift();
    }



}
