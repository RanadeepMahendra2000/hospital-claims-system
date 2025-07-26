package com.hospital.claims.controller;

import com.hospital.claims.entity.User;
import com.hospital.claims.repository.UserRepository;
import com.hospital.claims.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtils jwtUtils;

    // DTO for incoming JSON
    public static class AuthRequest {
        public String username;
        public String password;
    }

    // DTO for outgoing JSON
    public static class AuthResponse {
        public Long id;
        public String accessToken;
        public AuthResponse(Long id, String token) {
            this.id = id;
            this.accessToken = token;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest req) {
        // check for existing user?
        Optional<User> exists = userRepo.findByUsername(req.username);
        if (exists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        // create + save
        User u = new User();
        u.setUsername(req.username);
        u.setPassword(passwordEncoder.encode(req.password));
        u = userRepo.save(u);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(u.getId(), null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        // authenticate credentials
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        );
        // generate JWT
        String token = jwtUtils.generateToken(auth.getName());
        // lookup ID
        User u = userRepo.findByUsername(auth.getName()).orElseThrow();
        return ResponseEntity.ok(new AuthResponse(u.getId(), token));
    }
}
