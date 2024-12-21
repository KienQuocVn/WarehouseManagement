package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class test {
  public static void main(String[] args) {
    // Khởi tạo EntityManagerFactory với persistence-unit "CanXuatNhapKho"
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CanXuatNhapKho");

    // Khởi tạo EntityManager
    EntityManager em = emf.createEntityManager();

    // Kiểm tra kết nối và tạo bảng
    System.out.println("Kết nối Hibernate thành công!");
    System.out.println("Nếu không có lỗi, bảng sẽ được tạo trong SQL Server.");

    // Đóng EntityManager
    em.close();
    emf.close();
  }
}
