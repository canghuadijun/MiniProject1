package com.example.MiniProject1.services.impl;

import com.example.MiniProject1.models.User;
import com.example.MiniProject1.payload.Request.LoginRequest;
import com.example.MiniProject1.payload.Request.RegistrationRequest;
import com.example.MiniProject1.payload.Response.JwtResponse;
import com.example.MiniProject1.repositories.UserRepository;
import com.example.MiniProject1.security.jwt.CustomUserDetails;
import com.example.MiniProject1.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenUtil;
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }

    public User register(RegistrationRequest registrationRequest) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
//            return null;
            throw new IllegalArgumentException("Username is already taken");
        }
        // Tạo đối tượng User từ thông tin đăng ký
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        String role = registrationRequest.getRole();
        if (role == null) {
            user.setRole("ROLE_USER");
        } else {
            switch (role) {
                case "admin":
                    user.setRole("ROLE_ADMIN");
                    break;
                case "customer":
                    user.setRole("ROLE_CUSTOMER");
                    break;
                default:
                    user.setRole("ROLE_USER");
                    break;
            }
        }
        // Lưu user vào cơ sở dữ liệu
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // Không có người đăng nhập hiện tại
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal; // Trả về thông tin người đăng nhập hiện tại
        }

        return null; // Không tìm thấy thông tin người đăng nhập
    }
}