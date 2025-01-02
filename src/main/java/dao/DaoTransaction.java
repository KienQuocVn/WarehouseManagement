package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class DaoTransaction extends WHMA<Transaction, Integer> {

  @Override
  public void insert(Transaction entity) {
    String sql = "INSERT INTO Transactions (TransactionType, Date, Customer, Staff) VALUES (?, ?, ?, ?)";
    JdbcHelper.executeUpdate(sql,
        entity.getTransactionType(),
        entity.getDate(),
        entity.getCustomer());
  }

  @Override
  public void update(Transaction entity) {
    String sql = "UPDATE Transactions SET TransactionType = ?, Date = ?, Customer = ?, Staff = ? WHERE TransactionID = ?";
    JdbcHelper.executeUpdate(sql,
        entity.getTransactionType(),
        entity.getDate(),
        entity.getCustomer(),
        entity.getTransactionID());
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM Transactions WHERE TransactionID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<Transaction> selectAll() {
    String sql = "SELECT * FROM Transactions";
    return selectBySql(sql);
  }

  @Override
  public Transaction selectbyID(Integer id) {
    String sql = "SELECT * FROM Transactions WHERE TransactionID = ?";
    List<Transaction> transactions = selectBySql(sql, id);
    return transactions.isEmpty() ? null : transactions.get(0);
  }

  @Override
  public List<Transaction> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  // Common method to execute SQL queries and map results
  private List<Transaction> selectBySql(String sql, Object... args) {
    List<Transaction> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        Transaction transaction = new Transaction();
        transaction.setTransactionID(rs.getInt("TransactionID"));
        transaction.setTransactionType(rs.getString("TransactionType"));
        transaction.setDate(rs.getTimestamp("Date").toLocalDateTime());
        transaction.setCustomer(rs.getString("Customer"));

        // Assuming `User` object is retrieved elsewhere or a separate DaoUser method is used
        User staff = new User();
        staff.setId(rs.getInt("Staff"));

        list.add(transaction);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Transactions: " + e.getMessage(), e);
    }
    return list;
  }
}
