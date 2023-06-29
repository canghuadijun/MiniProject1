package com.example.MiniProject1.repositories;

import com.example.MiniProject1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Các phương thức truy vấn tương ứng với thao tác trên bảng User

    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}