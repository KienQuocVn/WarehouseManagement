package dao;

import AbtractClass.WHMA;
import Utils.JdbcHelper;
import model.Pallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoPallet extends WHMA<Pallet, Integer> {

  @Override
  public void insert(Pallet entity) {
    String sql = "INSERT INTO Pallets (LotID) VALUES (?)";
    JdbcHelper.executeUpdate(sql, entity.getLot().getLotID());
  }

  @Override
  public void update(Pallet entity) {
    String sql = "UPDATE Pallets SET LotID = ? WHERE PalletID = ?";
    JdbcHelper.executeUpdate(sql, entity.getLot().getLotID(), entity.getPalletID());
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM Pallets WHERE PalletID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<Pallet> selectAll() {
    String sql = "SELECT * FROM Pallets";
    return selectBySql(sql);
  }

  @Override
  public Pallet selectbyID(Integer id) {
    String sql = "SELECT * FROM Pallets WHERE PalletID = ?";
    List<Pallet> pallets = selectBySql(sql, id);
    return pallets.isEmpty() ? null : pallets.get(0);
  }

  public Pallet selectbyLotID(Integer id) {
    String sql = "SELECT * FROM Pallets WHERE LotID = ?";
    List<Pallet> pallets = selectBySql(sql, id);
    return pallets.isEmpty() ? null : pallets.get(0);
  }

  @Override
  public List<Pallet> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  private List<Pallet> selectBySql(String sql, Object... args) {
    List<Pallet> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        Pallet pallet = new Pallet();
        pallet.setPalletID(rs.getInt("PalletID"));
        pallet.setPalletIDU(rs.getString("palletIDU"));
        DaoLot daoLot = new DaoLot();
        pallet.setLot(daoLot.selectbyID(rs.getInt("LotID")));

        list.add(pallet);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Pallets: " + e.getMessage(), e);
    }
    return list;
  }
}
