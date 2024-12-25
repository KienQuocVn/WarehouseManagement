package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
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
}
