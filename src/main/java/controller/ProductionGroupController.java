package controller;

import Utils.DialogHelper;
import dao.DaoProductionGroup;
import dao.DaoShift;
import model.ProductionGroup;
import model.Shift;
import view.CaiDonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductionGroupController implements ActionListener {

    private CaiDonPanel caiDonPanel;
    private DaoProductionGroup daoproductionGroup;


    public ProductionGroupController(CaiDonPanel caiDonPanel) {
        this.caiDonPanel = caiDonPanel;
        this.daoproductionGroup = new DaoProductionGroup();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        switch (src) {
            case "Thêm":
                try {
                    handleAddProductionGroup();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Làm Mới":
                try {
                    ResetFormProductionGroup();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Sửa":
                try {
                    handleUpdateProductionGroup();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Xóa":
                try {
                    handleRemoveProductionGroup();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "Xóa Tất Cả":
                try {
                    handleRemoveAllProductionGroup();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }

        }
    }

    private void handleAddProductionGroup() {
        try {
            String ProductionGroupName = caiDonPanel.getTSXField().getText().trim(); // Loại bỏ khoảng trắng thừa


            // Kiểm tra các trường nhập liệu
            if (ProductionGroupName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Tổ Sản Xuất!");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }


            // Tạo đối tượng sản phẩm và thêm vào DB
            ProductionGroup shift = ProductionGroup.builder()
                    .groupName(ProductionGroupName)
                    .build();

            daoproductionGroup.insert(shift);

            DialogHelper.alert(caiDonPanel, "Thêm Tổ Sản Xuất thành công!");
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableDataProductionGroup();
            ResetFormProductionGroup();
        } catch (Exception ex) {
            // Kiểm tra nếu lỗi do vi phạm ràng buộc UNIQUE
            if (ex.getMessage().contains("UNIQUE")) { // Tùy vào driver SQL, thông báo lỗi có thể khác
                DialogHelper.alert(caiDonPanel, "Tên Tổ Sản Xuất đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi thêm Tổ Sản Xuất: " + ex.getMessage());
            }

        }
    }

    private void handleUpdateProductionGroup() {
        try {
            ProductionGroup ProductionGroupCaiDonPanel = caiDonPanel.getProductionGroupSendController();
            String ProductionGroupName = caiDonPanel.getTSXField().getText().trim();// Loại bỏ khoảng trắng thừa


            // Kiểm tra các trường nhập liệu
            if (ProductionGroupName.isEmpty()) {
                DialogHelper.alert(caiDonPanel, "Vui lòng nhập Tổ Sản Xuất !");
                return; // Dừng hàm nếu thông tin chưa đầy đủ
            }



            // Tạo đối tượng Product và cập nhật
            ProductionGroup updatedSProductionGroup = new ProductionGroup(ProductionGroupCaiDonPanel.getGroupID(), ProductionGroupName);

            daoproductionGroup.update(updatedSProductionGroup); // Gọi phương thức update từ DAO
            DialogHelper.alert(caiDonPanel, "Dữ liệu đã được cập nhật." );
            // Cập nhật bảng dữ liệu trong `CaiDonPanel`
            caiDonPanel.updateTableDataProductionGroup();
            ResetFormProductionGroup();
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE")) { // Tùy vào driver SQL, thông báo lỗi có thể khác
                DialogHelper.alert(caiDonPanel, "Tên Tổ Sản Xuất đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                DialogHelper.alert(caiDonPanel, "Lỗi khi cập nhật Tổ Sản Xuất: " + ex.getMessage());
            }
        }
    }

    private void handleRemoveProductionGroup() {
        try {
            // Lấy sản phẩm được chọn từ `caiDonPanel`
            ProductionGroup ProductionGroupCaiDonPanel = caiDonPanel.getProductionGroupSendController();

            if (ProductionGroupCaiDonPanel != null) {
                // Hiển thị hộp thoại xác nhận
                boolean confirmed = DialogHelper.confirm(caiDonPanel,
                        "Bạn có chắc muốn xóa Tổ Sản Xuất: " + ProductionGroupCaiDonPanel.getGroupName() + "?");

                // Nếu người dùng chọn "Yes", thực hiện xóa
                if (confirmed) {
                    // Thực hiện xóa sản phẩm (giả sử có phương thức `deleteProduct` trong DAO)
                    daoproductionGroup.delete(ProductionGroupCaiDonPanel.getGroupID());

                    // Hiển thị thông báo và cập nhật lại giao diện
                    DialogHelper.alert(caiDonPanel, "Tổ Sản Xuất đã được xóa thành công.");
                    caiDonPanel.updateTableDataProductionGroup();
                    ResetFormProductionGroup();
                }
            } else {
                // Nếu không có sản phẩm được chọn
                DialogHelper.alert(caiDonPanel, "Tổ Sản Xuất nào được chọn để xóa!");
            }
        } catch (Exception ex) {
            // Hiển thị thông báo lỗi
            DialogHelper.alert(caiDonPanel, "Lỗi khi Xóa Ca Sản Xuất: " + ex.getMessage());
        }
    }


    private void handleRemoveAllProductionGroup() {
        try {
            // Hiển thị hộp thoại xác nhận
            boolean confirmed = DialogHelper.confirm(caiDonPanel,
                    "Bạn có chắc muốn xóa toàn bộ Ca Sản Xuất không ?: ");

            // Nếu người dùng chọn "Yes", thực hiện xóa
            if (confirmed) {
                // Thực hiện xóa sản phẩm (giả sử có phương thức `deleteProduct` trong DAO)
                daoproductionGroup.deleteAll();
                // Hiển thị thông báo và cập nhật lại giao diện
                DialogHelper.alert(caiDonPanel, "Toàn bộ Ca Sản Xuất đã được xóa thành công.");
                caiDonPanel.updateTableDataProductionGroup();
                ResetFormProductionGroup();
            }

        } catch (Exception ex) {
            // Hiển thị thông báo lỗi
            DialogHelper.alert(caiDonPanel, "Lỗi khi Xóa Ca Sản Xuất: " + ex.getMessage());
        }
    }



    public void ResetFormProductionGroup () {
        caiDonPanel.getTSXField().setText("");
        caiDonPanel.getbtnThemProductionGroup().setEnabled(true);
        caiDonPanel.getbtnSuaProductionGroup().setEnabled(false);
        caiDonPanel.getbtnXoaProductionGroup().setEnabled(false);
        caiDonPanel.getbtnXoaTatCaProductionGroup().setEnabled(true);
        caiDonPanel.updateTableDataProductionGroup();
    }





}
