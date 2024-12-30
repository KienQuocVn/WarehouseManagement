package dao;

import AbtractClass.WHMA;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import model.Lot;
import Utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Pallet;
import model.Product;
import model.ProductionGroup;
import model.Shift;
import model.WarehouseStaff;

public class DaoLot extends WHMA<Lot, Integer> {
  private DaoLot daoLot;
  private DaoShift daoShift;
  private DaoProductionGroup daoProductionGroup;
  private DaoProduct daoProduct;
  private DaoWarehouseStaff daoWarehouseStaff;

  @Override
  public void insert(Lot entity) {
    String sql = "INSERT INTO Lots (LotIDU, ProductID, ProductionTime, ExpirationDate, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID) " + // Sửa cột
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    JdbcHelper.executeUpdate(
        sql,
        entity.getLotIDU(),
        entity.getProduct().getProductID(),
        entity.getProductionTime(),
        entity.getExpirationDate(),
        entity.getWeight(),
        entity.getWarehouseWeight(),
        entity.getWeightDeviation(),
        entity.getShift().getShiftId(),
        entity.getProductionGroup().getGroupID(),
        entity.getWarehouseStaff().getStaffId()
    );
  }

  @Override
  public void update(Lot entity) {
    String sql = "UPDATE Lots SET LotIDU = ?, ProductID = ?, ProductionTime = ?, ExpirationDate = ?, Weight = ?, WarehouseWeight = ?, WeightDeviation = ?, ShiftID = ?, GroupID = ?, WarehouseStaffID = ? " + // Sửa cột
        "WHERE LotID = ?";

    JdbcHelper.executeUpdate(
        sql,
        entity.getLotIDU(),
        entity.getProduct().getProductID(),
        entity.getProductionTime(),
        entity.getExpirationDate(),
        entity.getWeight(),
        entity.getWarehouseWeight(),
        entity.getWeightDeviation(),
        entity.getShift().getShiftId(),
        entity.getProductionGroup().getGroupID(),
        entity.getWarehouseStaff().getStaffId(),
        entity.getLotID()
    );
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM Lots WHERE LotID = ?";
    JdbcHelper.executeUpdate(sql, id);
  }

  @Override
  public List<Lot> selectAll() {
    String sql = "SELECT \n"
        + "            l.LotID,\n"
        + "            l.LotIDU,\n"
        + "            p.ProductID,\n"
        + "            p.ProductName,\n"
        + "            pg.GroupID,\n"
        + "            pg.GroupName,\n"
        + "            s.ShiftID,\n"
        + "            s.ShiftName,\n"
        + "            l.ProductionTime,\n"
        + "            l.ExpirationDate,\n"
        + "            l.Weight,\n"
        + "            l.WarehouseWeight,\n"
        + "            l.WeightDeviation,\n"
        + "            ws.StaffID,\n"
        + "            ws.StaffName,\n"
        + "            pal.PalletID\n"
        + "        FROM \n"
        + "            Lots l\n"
        + "        JOIN \n"
        + "            Products p ON l.ProductID = p.ProductID\n"
        + "        JOIN \n"
        + "            ProductionGroups pg ON l.GroupID = pg.GroupID\n"
        + "        JOIN \n"
        + "            Shifts s ON l.ShiftID = s.ShiftID\n"
        + "        JOIN \n"
        + "            WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID"
        + "        LEFT JOIN \n"
        + "            Pallets pal ON pal.LotID = l.LotID";
    return selectBySql(sql);
  }

  @Override
  public Lot selectbyID(Integer id) {
    String sql = "SELECT * FROM Lots WHERE LotID = ?";
    List<Lot> list = selectBySql(sql, id);
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public List<Lot> selectbyID(String sql, Object... args) {
    return selectBySql(sql, args);
  }

  private List<Lot> selectBySql(String sql, Object... args) {
    List<Lot> list = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
      while (rs.next()) {
        Lot lot = new Lot();
        lot.setLotID(rs.getInt("LotID"));
        lot.setLotIDU(rs.getString("LotIDU"));

        // Mapping khóa ngoại ProductID
        Product product = new Product();
        product.setProductID(rs.getInt("ProductID"));
        product.setProductName(rs.getString("ProductName")); // Ánh xạ thêm ProductName
        lot.setProduct(product);

        // Mapping các cột khác
        lot.setProductionTime(rs.getObject("ProductionTime", LocalDate.class));
        lot.setExpirationDate(rs.getObject("ExpirationDate",  LocalDate.class));
        lot.setWeight(rs.getBigDecimal("Weight"));
        lot.setWarehouseWeight(rs.getBigDecimal("WarehouseWeight"));
        lot.setWeightDeviation(rs.getBigDecimal("WeightDeviation"));

        // Mapping khóa ngoại ShiftID
        Shift shift = new Shift();
        shift.setShiftId(rs.getInt("ShiftID"));
        shift.setShiftName(rs.getString("ShiftName"));
        lot.setShift(shift);

        // Mapping khóa ngoại GroupID
        ProductionGroup productionGroup = new ProductionGroup();
        productionGroup.setGroupID(rs.getInt("GroupID"));
        productionGroup.setGroupName(rs.getString("GroupName"));
        lot.setProductionGroup(productionGroup);

        WarehouseStaff warehouseStaff = new WarehouseStaff();
        warehouseStaff.setStaffId(rs.getInt("StaffID")); // Truy vấn đúng cột StaffID
        warehouseStaff.setStaffName(rs.getString("StaffName"));
        lot.setWarehouseStaff(warehouseStaff);

        // Mapping Pallets
        Integer palletID = rs.getObject("PalletID", Integer.class);
        if (palletID != null) {
          Pallet pallet = new Pallet();
          pallet.setPalletID(palletID);
          pallet.setLot(lot); // Thiết lập mối quan hệ ngược
          lot.getPallets().add(pallet); // Thêm vào danh sách pallets
        }

        list.add(lot);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Lots: " + e.getMessage(), e);
    }
    return list;
  }

  public List<Lot> searchLots(String productionGroup, String shift, String productName, java.sql.Date fromDate, java.sql.Date toDate) {
    StringBuilder sql = new StringBuilder("""
        SELECT 
            l.LotID,
            l.LotIDU,
            p.ProductID,
            p.ProductName,
            pg.GroupID,
            pg.GroupName,
            s.ShiftID,
            s.ShiftName,
            l.ProductionTime,
            l.ExpirationDays,
            l.Weight,
            l.WarehouseWeight,
            l.WeightDeviation,
            ws.StaffID,
            ws.StaffName,
            pal.PalletID
        FROM Lots l
        JOIN Products p ON l.ProductID = p.ProductID
        JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
        JOIN Shifts s ON l.ShiftID = s.ShiftID
        JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
        LEFT JOIN Pallets pal ON pal.LotID = l.LotID
        WHERE 1=1
    """);

    List<Object> params = new ArrayList<>();

    // Điều kiện lọc
    if (productionGroup != null) {
      sql.append(" AND pg.GroupName = ?");
      params.add(productionGroup);
    }
    if (shift != null) {
      sql.append(" AND s.ShiftName = ?");
      params.add(shift);
    }
    if (productName != null) {
      sql.append(" AND p.ProductName = ?");
      params.add(productName);
    }
    if (fromDate != null) {
      sql.append(" AND l.ProductionTime >= ?");
      params.add(fromDate);
    }
    if (toDate != null) {
      sql.append(" AND l.ProductionTime <= ?");
      params.add(toDate);
    }

    // Sắp xếp kết quả
    sql.append(" ORDER BY l.ProductionTime DESC");

    // Trả về kết quả
    return selectBySql(sql.toString(), params.toArray());
  }



}
