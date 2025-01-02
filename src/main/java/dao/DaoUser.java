package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoUser extends WHMA<User, Integer> {

  @Override
  public void insert(User entity) {
    String sql = "INSERT INTO Users (FullName) VALUES (?)";
    JdbcHelper.executeUpdate(sql, entity.getFullName());
  }

  @Override
  public void update(User entity) {
    String sql = "UPDATE Users SET FullName = ? WHERE Id = ?";
    JdbcHelper.executeUpdate(sql, entity.getFullName(), entity.getId());
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM Users WHERE Id = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  public void deleteAll() {
    String sql = "DELETE FROM Users";
    JdbcHelper.executeUpdate(sql);
  }

  @Override
  public List<User> selectAll() {
    String sql = "SELECT * FROM Users";
    return selectBySql(sql);
  }

  @Override
  public User selectbyID(Integer id) {
    String sql = "SELECT * FROM Users WHERE Id = ?";
    List<User> users = selectBySql(sql, id);
    return users.isEmpty() ? null : users.get(0);
  }

  @Override
  public List<User> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  // Common method to execute SQL queries and map results
  private List<User> selectBySql(String sql, Object... args) {
    List<User> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        User user = new User();
        user.setId(rs.getInt("Id"));
        user.setFullName(rs.getString("FullName"));
        list.add(user);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Users: " + e.getMessage(), e);
    }
    return list;
  }
}
