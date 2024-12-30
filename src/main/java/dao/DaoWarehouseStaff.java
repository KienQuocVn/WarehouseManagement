package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.WarehouseStaff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoWarehouseStaff extends WHMA<WarehouseStaff, Integer> {

  @Override
  public void insert(WarehouseStaff entity) {
    String sql = "INSERT INTO WarehouseStaff (StaffName) VALUES (?)";
    JdbcHelper.executeUpdate(sql, entity.getStaffName());
  }

  @Override
  public void update(WarehouseStaff entity) {
    String sql = "UPDATE WarehouseStaff SET StaffName = ? WHERE StaffId = ?";
    JdbcHelper.executeUpdate(sql, entity.getStaffName(), entity.getStaffId());
  }

  public void deleteAll() {
    String sql = "DELETE FROM WarehouseStaff ";
    JdbcHelper.executeUpdate(sql);
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM WarehouseStaff WHERE StaffId = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<WarehouseStaff> selectAll() {
    String sql = "SELECT * FROM WarehouseStaff";
    return selectBySql(sql);
  }

  @Override
  public WarehouseStaff selectbyID(Integer id) {
    String sql = "SELECT * FROM WarehouseStaff WHERE StaffId = ?";
    List<WarehouseStaff> staffList = selectBySql(sql, id);
    return staffList.isEmpty() ? null : staffList.get(0);
  }

  @Override
  public List<WarehouseStaff> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  // Common method to execute SQL queries and map results
  private List<WarehouseStaff> selectBySql(String sql, Object... args) {
    List<WarehouseStaff> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        WarehouseStaff staff = new WarehouseStaff();
        staff.setStaffId(rs.getInt("StaffId"));
        staff.setStaffName(rs.getString("StaffName"));
        list.add(staff);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying WarehouseStaff: " + e.getMessage(), e);
    }
    return list;
  }

  public WarehouseStaff findByName(String staffName) {
    String sql = "SELECT * FROM WarehouseStaff WHERE StaffName = ?";
    try (Connection connection = JdbcHelper.getConnection(); // Obtain connection
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, staffName);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        WarehouseStaff staff = new WarehouseStaff();
        staff.setStaffId(rs.getInt("StaffId"));
        staff.setStaffName(rs.getString("StaffName"));
        return staff;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while finding WarehouseStaff by name: " + e.getMessage(), e);
    }
    return null;
  }


}
