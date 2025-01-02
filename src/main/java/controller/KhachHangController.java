package controller;

import Utils.DialogHelper;
import dao.DaoUser;
import model.User;
import view.CaiDonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KhachHangController implements ActionListener {

    private CaiDonPanel caiDonPanel;
    private DaoUser daoUser;

    public KhachHangController(CaiDonPanel caiDonPanel) {
        this.caiDonPanel = caiDonPanel;
        this.daoUser = new DaoUser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        switch (src) {
            case "Thêm":
                try {
                    handleAddUser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Làm Mới":
                try {
                    ResetFormUser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Sửa":
                try {
                    handleUpdateUser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Xóa":
                try {
                    handleRemoveUser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Xóa Tất Cả":
                try {
                    handleRemoveAllUser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    private void handleAddUser() {
        try {
            String fullName = caiDonPanel.getKhachHangField().getText().trim();

            if (fullName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Tên Khách Hàng!");
                return;
            }

            User user = User.builder()
                    .fullName(fullName)
                    .build();

            daoUser.insert(user);

            DialogHelper.alert(caiDonPanel, "Thêm Khách Hàng thành công!");
            caiDonPanel.updateTableDataKhachHang();
            ResetFormUser();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) {
                DialogHelper.alert(caiDonPanel, "Tên Khách Hàng đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi thêm Khách Hàng: " + ex.getMessage());
            }
        }
    }

    private void handleUpdateUser() {
        try {
            User userFromPanel = caiDonPanel.getKhachhangSendController();
            String fullName = caiDonPanel.getKhachHangField().getText().trim();

            if (fullName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Tên Khách Hàng!");
                return;
            }

            User updatedUser = new User(userFromPanel.getId(), fullName);
            daoUser.update(updatedUser);

            DialogHelper.alert(caiDonPanel, "Dữ liệu đã được cập nhật.");
            caiDonPanel.updateTableDataKhachHang();
            ResetFormUser();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) {
                DialogHelper.alert(caiDonPanel, "Tên Khách Hàng đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi cập nhật Khách Hàng: " + ex.getMessage());
            }
        }
    }

    private void handleRemoveUser() {
        try {
            User userFromPanel = caiDonPanel.getKhachhangSendController();

            if (userFromPanel != null) {
                boolean confirmed = DialogHelper.confirm(caiDonPanel,
                        "Bạn có chắc muốn xóa Khách Hàng: " + userFromPanel.getFullName() + "?");

                if (confirmed) {
                    daoUser.delete(userFromPanel.getId());
                    DialogHelper.alert(caiDonPanel, "Khách Hàng đã được xóa thành công.");
                    caiDonPanel.updateTableDataKhachHang();
                    ResetFormUser();
                }
            } else {
                DialogHelper.alert(caiDonPanel, "Chưa chọn Khách Hàng để xóa!");
            }
        } catch (Exception ex) {
            DialogHelper.alert(caiDonPanel, "Lỗi khi xóa Khách Hàng: " + ex.getMessage());
        }
    }

    private void handleRemoveAllUser() {
        try {
            boolean confirmed = DialogHelper.confirm(caiDonPanel, "Bạn có chắc muốn xóa toàn bộ Khách Hàng không?");

            if (confirmed) {
                daoUser.deleteAll();
                DialogHelper.alert(caiDonPanel, "Toàn bộ Khách Hàng đã được xóa thành công.");
                caiDonPanel.updateTableDataKhachHang();
                ResetFormUser();
            }
        } catch (Exception ex) {
            DialogHelper.alert(caiDonPanel, "Lỗi khi xóa toàn bộ Khách Hàng: " + ex.getMessage());
        }
    }

    public void ResetFormUser() {
        caiDonPanel.getKhachHangField().setText("");
        caiDonPanel.getBtnThemKhachHang().setEnabled(true);
        caiDonPanel.getBtnSuaKhachHang().setEnabled(false);
        caiDonPanel.getBtnXoaKhachHang().setEnabled(false);
        caiDonPanel.getBtnXoaTatCaKhachHang().setEnabled(true);
        caiDonPanel.updateTableDataKhachHang();
    }
}
