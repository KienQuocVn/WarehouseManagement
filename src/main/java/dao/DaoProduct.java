package dao;

import AbtractClass.WHMA;
import model.Product;
import Utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public List<Product> selectAll() {
        String sql = "SELECT * FROM Products";
        return selectBySql(sql);
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
}
