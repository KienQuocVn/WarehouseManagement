package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Product;
import model.ProductionGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoProductionGroup extends WHMA<ProductionGroup, Integer> {

  @Override
  public void insert(ProductionGroup entity) {
    String sql = "INSERT INTO ProductionGroups (GroupName) VALUES (?)";
    JdbcHelper.executeUpdate(sql, entity.getGroupName());
  }

  @Override
  public void update(ProductionGroup entity) {
    String sql = "UPDATE ProductionGroups SET GroupName = ? WHERE GroupID = ?";
    JdbcHelper.executeUpdate(sql, entity.getGroupName(), entity.getGroupID());
  }

  public void deleteAll() {
    String sql = "DELETE FROM ProductionGroups ";
    JdbcHelper.executeUpdate(sql);
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM ProductionGroups WHERE GroupID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<ProductionGroup> selectAll() {
    String sql = "SELECT * FROM ProductionGroups";
    return selectBySql(sql);
  }

  @Override
  public ProductionGroup selectbyID(Integer id) {
    String sql = "SELECT * FROM ProductionGroups WHERE GroupID = ?";
    List<ProductionGroup> groups = selectBySql(sql, id);
    return groups.isEmpty() ? null : groups.get(0);
  }

  @Override
  public List<ProductionGroup> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  // Phương thức chung để thực hiện truy vấn SQL
  private List<ProductionGroup> selectBySql(String sql, Object... args) {
    List<ProductionGroup> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        ProductionGroup group = new ProductionGroup();
        group.setGroupID(rs.getInt("GroupID"));
        group.setGroupName(rs.getString("GroupName"));
        list.add(group);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying ProductionGroups: " + e.getMessage(), e);
    }
    return list;
  }

  public ProductionGroup findByName(String ProductionGroupName) {
    String sql = "SELECT * FROM ProductionGroups WHERE GroupName = ?";
    try (Connection connection = JdbcHelper.getConnection(); // Obtain connection
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, ProductionGroupName);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        ProductionGroup groups = new ProductionGroup();
        groups.setGroupID(rs.getInt("GroupId"));
        groups.setGroupName(rs.getString("GroupName"));
        return groups;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while finding ProductionGroup by name: " + e.getMessage(), e);
    }
    return null;
  }
}
