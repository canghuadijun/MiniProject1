package com.example.MiniProject1.controller;
import javax.validation.constraints.NotBlank;
import com.example.MiniProject1.models.User;
import com.example.MiniProject1.payload.Request.LoginRequest;
import com.example.MiniProject1.payload.Request.RegistrationRequest;
import com.example.MiniProject1.payload.Response.AuthenticationResponse;
import com.example.MiniProject1.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> testApi() {
        return ResponseEntity.ok().body("Hê lô");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        User user = authService.register(registrationRequest);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
