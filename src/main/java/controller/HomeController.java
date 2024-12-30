package controller;

import dao.DaoProduct;
import model.Product;
import view.CaiDonPanel;
import view.HomePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener {

    private HomePanel homePanel;
    private DaoProduct daoProduct;

    public HomeController(HomePanel homePanel) {
        this.homePanel = homePanel;
        this.daoProduct = new DaoProduct();

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


    }
}
