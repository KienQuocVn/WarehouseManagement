package dao;

import AbtractClass.WHMA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Product;
import Utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.WarehouseStaff;

public class DaoProduct extends WHMA<Product, Integer> {

    @Override
    public void insert(Product entity) {
        String sql = "INSERT INTO Products (ProductName, HSD, Color) VALUES ( ?, ?, ?)";
        JdbcHelper.executeUpdate(sql, entity.getProductName(), entity.getHSD(), entity.getColor());
    }

    @Override
    public void update(Product entity) {
        String sql = "UPDATE Products SET ProductName = ?, HSD = ?, Color = ? WHERE ProductID = ?";
        JdbcHelper.executeUpdate(sql, entity.getProductName(), entity.getHSD(), entity.getColor(), entity.getProductID());
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        JdbcHelper.executeUpdate(sql, id.toString());
    }


    public void deleteAll() {
        String sql = "DELETE FROM Products ";
        JdbcHelper.executeUpdate(sql);
    }

    @Override
    public List<Product> selectAll() {
        String sql = "SELECT * FROM Products";
        return selectBySql(sql);
    }


    public Product selectbyName(String name) {
        String sql = "SELECT * FROM Products WHERE ProductName = ?";
        List<Product> products = selectBySql(sql, name);
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public Product selectbyID(Integer id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        List<Product> products = selectBySql(sql, id.toString());
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public List<Product> selectbyID(String sql, Object... args) {
        return selectBySql(sql, args);
    }

    public List<String> getAllProductNames() {
        List<String> productNames = new ArrayList<>();
        String sql = "SELECT ProductName FROM Products";
        try (ResultSet rs = JdbcHelper.executeQuery(sql)) {
            while (rs.next()) {
                productNames.add(rs.getString("ProductName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching product names: " + e.getMessage());
        }
        return productNames;
    }

    private List<Product> selectBySql(String sql, Object... args) {
        List<Product> list = new ArrayList<>();
        try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductID((rs.getInt("ProductID")));
                product.setProductName(rs.getString("ProductName"));
                product.setHSD(rs.getDouble("HSD"));
                product.setColor(rs.getString("Color"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying products: " + e.getMessage());
        }
        return list;
    }

    public String getStaffNameByProductName(String productName) {
        String sql = "SELECT ws.StaffName " +
            "FROM Products p " +
            "JOIN Lots l ON p.productID = l.ProductID " +
            "JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.staffId " +
            "WHERE p.ProductName = ?";

        List<String> staffNames = new ArrayList<>();
        try (Connection conn = JdbcHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the product name parameter
            stmt.setString(1, productName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    staffNames.add(rs.getString("StaffName"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching staff name: " + e.getMessage());
        }

        return staffNames.isEmpty() ? null : staffNames.get(0);
    }
}
