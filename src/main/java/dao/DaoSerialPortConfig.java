package dao;


import Utils.JdbcHelper;
import model.SerialPortConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoSerialPortConfig {

    public void insert(SerialPortConfig entity) {
        String sql = "INSERT INTO SerialPortConfig (comPort, baudRate, dataBits, stopBits, parityBits) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql, entity.getComPort(), entity.getBaudRate(), entity.getDataBits(), entity.getStopBits(), entity.getParityBits());
    }

    public void update(SerialPortConfig entity) {
        String sql = "UPDATE SerialPortConfig SET comPort = ?, baudRate = ?, dataBits = ?, stopBits = ?, parityBits = ? WHERE Id = ?";
        JdbcHelper.executeUpdate(sql, entity.getComPort(), entity.getBaudRate(), entity.getDataBits(), entity.getStopBits(), entity.getParityBits(), entity.getId());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM SerialPortConfig WHERE Id = ?";
        JdbcHelper.executeUpdate(sql, id);
    }

    public List<SerialPortConfig> selectAll() {
        String sql = "SELECT * FROM SerialPortConfig";
        return selectBySql(sql);
    }

    public SerialPortConfig selectById(Integer id) {
        String sql = "SELECT * FROM SerialPortConfig WHERE Id = ?";
        List<SerialPortConfig> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<SerialPortConfig> selectBySql(String sql, Object... args) {
        List<SerialPortConfig> list = new ArrayList<>();
        try (ResultSet rs = JdbcHelper.executeQuery(sql, args)) {
            while (rs.next()) {
                SerialPortConfig entity = SerialPortConfig.builder()
                        .Id(rs.getInt("Id"))
                        .comPort(rs.getString("comPort"))
                        .baudRate(rs.getInt("baudRate"))
                        .dataBits(rs.getInt("dataBits"))
                        .stopBits(rs.getInt("stopBits"))
                        .parityBits(rs.getString("parityBits"))
                        .build();
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while querying SerialPortConfig: " + e.getMessage(), e);
        }
        return list;
    }
}