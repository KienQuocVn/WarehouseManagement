package dao;

import Utils.JdbcHelper;
import model.SettingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DaoSettingSystem {

    public void insert(SettingSystem entity) {
        String sql = "INSERT INTO SettingSystem (DirSave, NameFile, WeightThreshold, Printer) VALUES (?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql, entity.getDirSave(), entity.getNameFile(), entity.getWeightThreshold(), entity.getPrinter());
    }

    public void update(SettingSystem entity) {
        String sql = "UPDATE SettingSystem SET DirSave = ?, NameFile = ?, WeightThreshold = ?, Printer = ? WHERE Id = ?";
        JdbcHelper.executeUpdate(sql, entity.getDirSave(), entity.getNameFile(), entity.getWeightThreshold(), entity.getPrinter(), entity.getId());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM SettingSystem WHERE Id = ?";
        JdbcHelper.executeUpdate(sql, id);
    }

    public List<SettingSystem> selectAll() {
        String sql = "SELECT * FROM SettingSystem";
        return selectBySql(sql);
    }

    public SettingSystem selectById(Integer id) {
        String sql = "SELECT * FROM SettingSystem WHERE Id = ?";
        List<SettingSystem> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public SettingSystem selectLatest() {
        String sql = "SELECT TOP 1 * FROM SettingSystem ORDER BY Id DESC"; // Lấy cài đặt mới nhất
        List<SettingSystem> list = selectBySql(sql);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<SettingSystem> selectBySql(String sql, Object... args) {
        List<SettingSystem> list = new ArrayList<>();
        try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
            while (rs.next()) {
                SettingSystem entity = SettingSystem.builder()
                        .Id(rs.getInt("Id"))
                        .DirSave(rs.getString("DirSave"))
                        .NameFile(rs.getString("NameFile"))
                        .WeightThreshold(rs.getBigDecimal("WeightThreshold"))
                        .Printer(rs.getString("Printer"))
                        .build();
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying SettingSystem: " + e.getMessage(), e);
        }
        return list;
    }
}

