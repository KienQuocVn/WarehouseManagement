package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import model.Lot;
import model.Pallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoPallet extends WHMA<Pallet, Integer> {

  @Override
  public void insert(Pallet entity) {
    String sql = "INSERT INTO Pallets (palletIDU, LotID) VALUES (?, ?)";
    JdbcHelper.executeUpdate(sql, entity.getPalletIDU(), entity.getLot().getLotID());
  }

  @Override
  public void update(Pallet entity) {
    String sql = "UPDATE Pallets SET palletIDU = ?, LotID = ? WHERE palletID = ?";
    JdbcHelper.executeUpdate(sql, entity.getPalletIDU(), entity.getLot().getLotID(), entity.getPalletID());
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM Pallets WHERE palletID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<Pallet> selectAll() {
    String sql = "SELECT * FROM Pallets";
    return selectBySql(sql);
  }

  @Override
  public Pallet selectbyID(Integer id) {
    String sql = "SELECT * FROM Pallets WHERE palletID = ?";
    List<Pallet> pallets = selectBySql(sql, id);
    return pallets.isEmpty() ? null : pallets.get(0);
  }

  public Pallet selectByLotID(Integer lotID) {
    String sql = "SELECT * FROM Pallets WHERE LotID = ?";
    List<Pallet> pallets = selectBySql(sql, lotID);
    return pallets.isEmpty() ? null : pallets.get(0);
  }

  public void deleteByLotId(Integer id) {
    String sql = "DELETE FROM Pallets WHERE LotID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }



  @Override
  public List<Pallet> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  // Common method to execute SQL queries and map results
  private List<Pallet> selectBySql(String sql, Object... args) {
    List<Pallet> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        Pallet pallet = new Pallet();
        pallet.setPalletID(rs.getInt("palletID"));
        pallet.setPalletIDU(rs.getString("palletIDU"));

        // Tạo đối tượng Lot nếu cần thiết, chỉ set LotID
        Lot lot = new Lot();
        lot.setLotID(rs.getInt("LotID"));
        pallet.setLot(lot);

        list.add(pallet);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Pallets: " + e.getMessage(), e);
    }
    return list;
  }
}
