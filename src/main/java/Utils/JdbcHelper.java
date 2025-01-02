package Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class JdbcHelper {

	private static final Dotenv dotenv = Dotenv.configure().load();

	private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String dburl = dotenv.get("DB_URL");
	private static final String username = dotenv.get("DB_USERNAME");
	private static final String password = dotenv.get("DB_PASSWORD");

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("Failed to load database driver: " + ex.getMessage(), ex);
		}
	}

	// Kết nối với cơ sở dữ liệu
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dburl, username, password);
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
			Connection con = getConnection(); // Kết nối cần được đóng bởi người gọi
			PreparedStatement stmt = prepareStatement(con, sql, args);
			return stmt.executeQuery(); // Kết quả cần được xử lý bởi người gọi
		} catch (SQLException e) {
			throw new RuntimeException("Error executing query: " + e.getMessage(), e);
		}
	}


	// Thực thi câu lệnh INSERT và trả về khóa sinh tự động
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

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dburl, username, password);
	}

}
