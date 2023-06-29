package com.example.MiniProject1.repositories;

import com.example.MiniProject1.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Các phương thức truy vấn tương ứng với thao tác trên bảng Customer
}
