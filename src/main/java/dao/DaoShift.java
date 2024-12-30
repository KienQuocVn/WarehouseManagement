package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.ProductionGroup;
import model.Shift;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoShift extends WHMA<Shift, Integer> {

  @Override
  public void insert(Shift entity) {
    String sql = "INSERT INTO Shifts (ShiftName) VALUES (?)";
    JdbcHelper.executeUpdate(sql, entity.getShiftName());
  }

  @Override
  public void update(Shift entity) {
    String sql = "UPDATE Shifts SET ShiftName = ? WHERE ShiftID = ?";
    JdbcHelper.executeUpdate(sql, entity.getShiftName(), entity.getShiftId());
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM Shifts WHERE ShiftID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  public void deleteAll() {
    String sql = "DELETE FROM Shifts ";
    JdbcHelper.executeUpdate(sql);
  }

  @Override
  public List<Shift> selectAll() {
    String sql = "SELECT * FROM Shifts";
    return selectBySql(sql);
  }

  @Override
  public Shift selectbyID(Integer id) {
    String sql = "SELECT * FROM Shifts WHERE ShiftID = ?";
    List<Shift> shifts = selectBySql(sql, id);
    return shifts.isEmpty() ? null : shifts.get(0);
  }

  public List<String> getAllShiftNames() {
    List<String> productNames = new ArrayList<>();
    String sql = "SELECT ShiftName FROM Shifts";
    try (ResultSet rs = JdbcHelper.executeQuery(sql)) {
      while (rs.next()) {
        productNames.add(rs.getString("ShiftName"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while fetching Shifts names: " + e.getMessage());
    }
    return productNames;
  }

  @Override
  public List<Shift> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  // Common method to execute SQL queries and map results
  private List<Shift> selectBySql(String sql, Object... args) {
    List<Shift> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        Shift shift = new Shift();
        shift.setShiftId(rs.getInt("ShiftID"));
        shift.setShiftName(rs.getString("ShiftName"));
        list.add(shift);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Shifts: " + e.getMessage(), e);
    }
    return list;
  }

  public Shift selectbyName(String name) {
    String sql = "SELECT * FROM Shifts WHERE ShiftName = ?";
    List<Shift> shifts = selectBySql(sql, name);
    return shifts.isEmpty() ? null : shifts.get(0);
  }
  public Shift findByName(String shiftName) {
    String sql = "SELECT * FROM Shifts WHERE ShiftName = ?";
    try (Connection connection = JdbcHelper.getConnection(); // Obtain connection
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, shiftName);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        Shift shift = new Shift();
        shift.setShiftId(rs.getInt("ShiftId"));
        shift.setShiftName(rs.getString("ShiftName"));
        return shift;
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while finding Shift by name: " + e.getMessage(), e);
    }
    return null;
  }
}
