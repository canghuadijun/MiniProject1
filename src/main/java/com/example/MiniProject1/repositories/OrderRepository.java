package com.example.MiniProject1.repositories;
import com.example.MiniProject1.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Các phương thức truy vấn tương ứng với thao tác trên bảng Order
}