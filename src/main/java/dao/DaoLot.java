package dao;

import AbtractClass.WHMA;

import java.sql.*;

import model.Lot;
import Utils.JdbcHelper;

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

  @Override
  public void insert(Lot entity) {
    String sql = """
        INSERT INTO Lots 
        (LotIDU, ProductID, ProductionTime, ExpirationDate, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

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

  // Update an existing Lot
  @Override
  public void update(Lot entity) {
    String sql = """
        UPDATE Lots SET 
        LotIDU = ?,
        ProductID = ?, 
        GroupID = ?, 
        ShiftID = ?, 
        ProductionTime = ?, 
        ExpirationDate = ?, 
        Weight = ?, 
        WarehouseWeight = ?, 
        WeightDeviation = ?, 
        WarehouseStaffID = ?,
        Status = ?
        WHERE LotID = ?
        """;

    JdbcHelper.executeUpdate(
        sql,
        entity.getLotIDU(),
        entity.getProduct().getProductID(),
        entity.getProductionGroup().getGroupID(),
        entity.getShift().getShiftId(),
        entity.getProductionTime(),
        entity.getExpirationDate(),
        entity.getWeight(),
        entity.getWarehouseWeight(),
        entity.getWeightDeviation(),
        entity.getWarehouseStaff().getStaffId(),
        entity.getStatus(),
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
    String sql = """
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
            l.ExpirationDate,
            l.Weight,
            l.WarehouseWeight,
            l.WeightDeviation,
            ws.StaffID,
            ws.StaffName,
            pal.PalletID,
            pal.palletIDU,
            l.Status
        FROM Lots l
        JOIN Products p ON l.ProductID = p.ProductID
        JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
        JOIN Shifts s ON l.ShiftID = s.ShiftID
        JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
        LEFT JOIN Pallets pal ON pal.LotID = l.LotID
        """;
    return selectBySql(sql);
  }


  @Override
  public Lot selectbyID(Integer id) {
    String sql = """
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
            l.ExpirationDate,
            l.Weight,
            l.WarehouseWeight,
            l.WeightDeviation,
            ws.StaffID,
            ws.StaffName,
            pal.PalletID,
            l.Status
        FROM Lots l
        JOIN Products p ON l.ProductID = p.ProductID
        JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
        JOIN Shifts s ON l.ShiftID = s.ShiftID
        JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
        LEFT JOIN Pallets pal ON pal.LotID = l.LotID
        WHERE l.LotID = ?
        """;
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
        product.setProductName(rs.getString("ProductName"));
        lot.setProduct(product);

        // Mapping các cột khác
        lot.setProductionTime(rs.getObject("ProductionTime", LocalDate.class));
        lot.setExpirationDate(rs.getObject("ExpirationDate",  LocalDate.class));
        lot.setWeight(rs.getBigDecimal("Weight"));
        lot.setWarehouseWeight(rs.getBigDecimal("WarehouseWeight"));
        lot.setWeightDeviation(rs.getBigDecimal("WeightDeviation"));
        lot.setStatus(rs.getString("Status"));

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
        warehouseStaff.setStaffId(rs.getInt("StaffID"));
        warehouseStaff.setStaffName(rs.getString("StaffName"));
        lot.setWarehouseStaff(warehouseStaff);

        // Mapping danh sách Pallet (nếu có)
        Integer palletID = rs.getObject("PalletID", Integer.class);
        if (palletID != null) { // Nếu có pallet liên kết
          Pallet pallet = new Pallet();
          pallet.setPalletID(palletID);
          pallet.setLot(lot);
          lot.getPallets().add(pallet);
        }

        list.add(lot);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error while querying Lots: " + e.getMessage(), e);
    }
    return list;
  }

  public List<Lot> searchByLotIDUOrPalletIDU(String id) {
    String sql = """
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
            l.ExpirationDate,
            l.Weight,
            l.WarehouseWeight,
            l.WeightDeviation,
            ws.StaffID,
            ws.StaffName,
            pal.PalletID,
            pal.PalletIDU,
            l.Status
        FROM Lots l
        JOIN Products p ON l.ProductID = p.ProductID
        JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
        JOIN Shifts s ON l.ShiftID = s.ShiftID
        JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
        LEFT JOIN Pallets pal ON pal.LotID = l.LotID
        WHERE (l.LotIDU = ? OR  p.ProductName = ?)
          AND (l.Status IS NULL OR l.Status = '')
        """;
    return selectBySql(sql, id, id);
  }


  public int insertAndGetID(Lot lot) {
    String sql = "INSERT INTO Lots (LotIDU, ProductID, ProductionTime, ExpirationDate, Weight, WarehouseWeight, WeightDeviation, ShiftID, GroupID, WarehouseStaffID) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = JdbcHelper.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      // Gán giá trị cho các tham số
      ps.setString(1, lot.getLotIDU());
      ps.setInt(2, lot.getProduct().getProductID());
      ps.setObject(3, lot.getProductionTime());
      ps.setObject(4, lot.getExpirationDate());
      ps.setBigDecimal(5, lot.getWeight());
      ps.setBigDecimal(6, lot.getWarehouseWeight());
      ps.setBigDecimal(7, lot.getWeightDeviation());
      ps.setInt(8, lot.getShift().getShiftId());
      ps.setInt(9, lot.getProductionGroup().getGroupID());
      ps.setInt(10, lot.getWarehouseStaff().getStaffId());

      // Thực thi câu lệnh và lấy khóa tự động sinh
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getInt(1); // Lấy giá trị LotID
        }
      }

    } catch (SQLException ex) {
      throw new RuntimeException("Lỗi khi chèn Lot và lấy ID: " + ex.getMessage(), ex);
    }

    return -1; // Trả về -1 nếu không thành công
  }


  public List<Lot> searchLots(String productionGroup, String shift, String productName, LocalDate fromDate, LocalDate toDate, String status) {

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
            l.ExpirationDate,
            l.Weight,
            l.WarehouseWeight,
            l.WeightDeviation,
            ws.StaffID,
            ws.StaffName,
            pal.PalletID,
            pal.palletIDU,
            l.Status
        FROM Lots l
        JOIN Products p ON l.ProductID = p.ProductID
        JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
        JOIN Shifts s ON l.ShiftID = s.ShiftID
        JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
        LEFT JOIN Pallets pal ON pal.LotID = l.LotID
        WHERE 1=1
        """);

    List<Object> params = new ArrayList<>();

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
      params.add(java.sql.Date.valueOf(fromDate));
    }
    if (toDate != null) {
      sql.append(" AND l.ProductionTime <= ?");
      params.add(java.sql.Date.valueOf(toDate));
    }
    if (status != null) {
      sql.append(" AND l.Status = ?");
      params.add(status);
    }
    sql.append(" ORDER BY l.ProductionTime DESC");

    return selectBySql(sql.toString(), params.toArray());
  }

  public void deleteLotById(int lotId) {
    String sql = "{CALL DeleteLot(?)}";

    try (Connection connection = JdbcHelper.getConnection();
        CallableStatement stmt = connection.prepareCall(sql)) {

      stmt.setInt(1, lotId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteAllLots() {
    String sql = "{CALL DeleteAllLots()}"; // Gọi stored procedure xóa tất cả

    try (Connection connection = JdbcHelper.getConnection();
        CallableStatement stmt = connection.prepareCall(sql)) {

      // Thực thi câu lệnh xóa tất cả dữ liệu
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<String> getLotIDUByProductID(int productID) {
    String sql = "SELECT LotIDU FROM Lots WHERE ProductID = ?";
    List<String> lotIDs = new ArrayList<>();
    try (ResultSet rs = JdbcHelper.executeQuery(sql, productID)) {
      while (rs.next()) {
        lotIDs.add(rs.getString("LotIDU"));
      }
    } catch (SQLException ex) {
      ex.printStackTrace(); // Xử lý lỗi
    }
    return lotIDs;
  }

  public List<Lot> selectAllStatus() {
    String sql = """
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
            l.ExpirationDate,
            l.Weight,
            l.WarehouseWeight,
            l.WeightDeviation,
            ws.StaffID,
            ws.StaffName,
            pal.PalletID,
            pal.palletIDU,
            l.Status
        FROM Lots l
        JOIN Products p ON l.ProductID = p.ProductID
        JOIN ProductionGroups pg ON l.GroupID = pg.GroupID
        JOIN Shifts s ON l.ShiftID = s.ShiftID
        JOIN WarehouseStaff ws ON l.WarehouseStaffID = ws.StaffID
        LEFT JOIN Pallets pal ON pal.LotID = l.LotID
        WHERE l.Status IS NULL OR l.Status = ''
        """;
    return selectBySql(sql);
  }

}