package database;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class ConnectDB {
  private static final EntityManagerFactory emf;

  // Nạp thông tin từ .env và cấu hình EntityManagerFactory
  static {
    // Load .env file
    Dotenv dotenv = Dotenv.configure().load();

    // Lấy giá trị từ .env
    String dbUrl = dotenv.get("DB_URL");
    String dbUsername = dotenv.get("DB_USERNAME");
    String dbPassword = dotenv.get("DB_PASSWORD");

    // Tạo map chứa các property để override
    Map<String, String> properties = new HashMap<>();
    properties.put("hibernate.connection.url", dbUrl);
    properties.put("hibernate.connection.username", dbUsername);
    properties.put("hibernate.connection.password", dbPassword);

    // Tạo EntityManagerFactory với persistence-unit "CanXuatNhapKho"
    emf = Persistence.createEntityManagerFactory("CanXuatNhapKho", properties);
  }

  public static void main(String[] args) {
    // Khởi tạo EntityManager
    EntityManager em = emf.createEntityManager();

    try {
      // Kiểm tra kết nối và thông báo thành công
      em.getTransaction().begin();
      System.out.println("Kết nối Hibernate thành công!");
      em.getTransaction().commit();
    } catch (Exception e) {
      System.err.println("Lỗi kết nối Hibernate: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Đóng EntityManager và EntityManagerFactory
      em.close();
      emf.close();
    }
  }
}
