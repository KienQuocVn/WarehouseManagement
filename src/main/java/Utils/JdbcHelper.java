package Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class JdbcHelper {

	private static final Dotenv dotenv = Dotenv.configure().load();

	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = dotenv.get("DB_URL");
	private static final String DB_USERNAME = dotenv.get("DB_USERNAME");
	private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("Failed to load database driver: " + ex.getMessage(), ex);
		}
	}

	// Kết nối với cơ sở dữ liệu
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	// Chuẩn bị câu lệnh SQL
	public static PreparedStatement prepareStatement(Connection con, String sql, Object... args) throws SQLException {
		PreparedStatement pstmt = sql.trim().startsWith("{")
				? con.prepareCall(sql)
				: con.prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			pstmt.setObject(i + 1, args[i]);
		}
		return pstmt;
	}

	// Thực thi câu lệnh UPDATE/INSERT/DELETE
	public static void executeUpdate(String sql, Object... args) {
		try (Connection con = getConnection();
			 PreparedStatement stmt = prepareStatement(con, sql, args)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error executing update: " + e.getMessage(), e);
		}
	}

	// Thực thi câu lệnh SELECT
	public static ResultSet executeQuery(String sql, Object... args) {
		try {
			Connection con = getConnection();
			PreparedStatement stmt = prepareStatement(con, sql, args);
			return stmt.executeQuery(); // Kết quả cần được xử lý và đóng bởi người gọi
		} catch (SQLException e) {
			throw new RuntimeException("Error executing query: " + e.getMessage(), e);
		}
	}

	// Thực thi câu lệnh INSERT và trả về khóa tự động sinh
	public static ResultSet executeQueryWithGeneratedKeys(String sql, Object... args) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			ps.executeUpdate();
			return ps.getGeneratedKeys(); // Người gọi cần xử lý và đóng ResultSet
		} catch (SQLException e) {
			throw new RuntimeException("Error executing query with generated keys: " + e.getMessage(), e);
		}
	}

	// Đóng tài nguyên (Connection, PreparedStatement, ResultSet)
	public static void closeResources(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException ignored) {}
		try {
			if (ps != null) ps.close();
		} catch (SQLException ignored) {}
		try {
			if (con != null) con.close();
		} catch (SQLException ignored) {}
	}
}
