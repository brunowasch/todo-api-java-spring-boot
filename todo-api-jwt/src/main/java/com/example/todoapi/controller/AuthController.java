
package com.example.todoapi.controller;

import com.example.todoapi.dto.AuthRequest;
import com.example.todoapi.entity.User;
import com.example.todoapi.repository.UserRepository;
import com.example.todoapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public User register(@RequestBody AuthRequest request) {
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(passwordEncoder.encode(request.password));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByUsername(request.username)
                .orElseThrow();

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(user.getUsername());

        return Map.of("token", token);
    }
}
