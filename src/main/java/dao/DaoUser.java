package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoUser extends WHMA<User, String> {

  @Override
  public void insert(User entity) {
    String sql = "INSERT INTO Users (Username, FullName, Role) VALUES (?, ?, ?)";
    JdbcHelper.executeUpdate(sql, entity.getUsername(), entity.getFullName(), entity.getRole());
  }

  @Override
  public void update(User entity) {
    String sql = "UPDATE Users SET FullName = ?, Role = ? WHERE Username = ?";
    JdbcHelper.executeUpdate(sql, entity.getFullName(), entity.getRole(), entity.getUsername());
  }

  @Override
  public void delete(String username) {
    String sql = "DELETE FROM Users WHERE Username = ?";
    JdbcHelper.executeUpdate(sql, username);
  }

  @Override
  public List<User> selectAll() {
    String sql = "SELECT * FROM Users";
    return selectBySql(sql);
  }

  @Override
  public User selectbyID(String username) {
    String sql = "SELECT * FROM Users WHERE Username = ?";
    List<User> users = selectBySql(sql, username);
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
        user.setUsername(rs.getString("Username"));
        user.setFullName(rs.getString("FullName"));
        user.setRole(rs.getString("Role"));
        list.add(user);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Users: " + e.getMessage(), e);
    }
    return list;
  }
}
