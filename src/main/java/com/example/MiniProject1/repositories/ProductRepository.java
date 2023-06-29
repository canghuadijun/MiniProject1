package com.example.MiniProject1.repositories;

import com.example.MiniProject1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String keyword);
    Boolean existsByName(String name);
    // Các phương thức truy vấn tương ứng với thao tác trên bảng Product
}
