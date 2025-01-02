package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import model.TransactionDetail;
import model.Transaction;
import model.Lot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoTransactionDetail extends WHMA<TransactionDetail, Integer> {

  @Override
  public void insert(TransactionDetail entity) {
    String sql = "INSERT INTO TransactionDetails (TransactionID, LotID, Quantity) VALUES (?, ?, ?)";
    JdbcHelper.executeUpdate(sql,
        entity.getTransaction().getTransactionID(),
        entity.getLot().getLotID(),
        entity.getQuantity());
  }

  @Override
  public void update(TransactionDetail entity) {
    String sql = "UPDATE TransactionDetails SET TransactionID = ?, LotID = ?, Quantity = ? WHERE DetailID = ?";
    JdbcHelper.executeUpdate(sql,
        entity.getTransaction().getTransactionID(),
        entity.getLot().getLotID(),
        entity.getQuantity(),
        entity.getDetailID());
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM TransactionDetails WHERE DetailID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<TransactionDetail> selectAll() {
    String sql = "SELECT * FROM TransactionDetails";
    return selectBySql(sql);
  }

  @Override
  public TransactionDetail selectbyID(Integer id) {
    String sql = "SELECT * FROM TransactionDetails WHERE DetailID = ?";
    List<TransactionDetail> details = selectBySql(sql, id);
    return details.isEmpty() ? null : details.get(0);
  }

  @Override
  public List<TransactionDetail> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  private List<TransactionDetail> selectBySql(String sql, Object... args) {
    List<TransactionDetail> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        TransactionDetail detail = new TransactionDetail();
        detail.setDetailID(rs.getInt("DetailID"));

        Transaction transaction = new Transaction();
        transaction.setTransactionID(rs.getInt("TransactionID"));
        detail.setTransaction(transaction);

        Lot lot = new Lot();
        lot.setLotID(rs.getInt("LotID"));
        detail.setLot(lot);

        detail.setQuantity(rs.getDouble("Quantity"));

        list.add(detail);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying TransactionDetails: " + e.getMessage(), e);
    }
    return list;
  }

  public List<Object[]> selectTransactionDetails() {
    String sql = "SELECT " +
        "l.LotID, " +
        "u.FullName, " +
        "t.Date, " +
        "ws.StaffName " +
        "FROM TransactionDetails td " +
        "JOIN Lots l ON td.LotID = l.LotID " +
        "JOIN Transactions t ON td.TransactionID = t.TransactionID " +
        "JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.staffId " +
        "JOIN Users u ON t.Staff = u.FullName"; // Đổi [Users] thành Users nếu không cần ngoặc vuông
    return selectBySql1(sql);
  }

  private List<Object[]> selectBySql1(String sql) {
    List<Object[]> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql)) { // Bỏ args nếu không cần
      while (rs.next()) {
        Object[] result = new Object[4];
        result[0] = rs.getInt("LotID");             // LotID
        result[1] = rs.getString("FullName");       // Username
        result[2] = rs.getTimestamp("Date").toLocalDateTime(); // Date (converted to LocalDateTime)
        result[3] = rs.getString("StaffName");      // StaffName
        list.add(result);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying TransactionDetails: " + e.getMessage(), e);
    }
    return list;
  }
}
