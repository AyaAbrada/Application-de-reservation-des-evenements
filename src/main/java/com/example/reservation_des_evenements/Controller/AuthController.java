package com.example.reservation_des_evenements.Controller;
import com.example.reservation_des_evenements.Configuration.JwtUtils;
import com.example.reservation_des_evenements.Repositories.UserRepositorie;
import com.example.reservation_des_evenements.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UserRepositorie userRepositorie;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
@Autowired
    public AuthController(JwtUtils jwtUtils, UserRepositorie userRepositorie, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.userRepositorie = userRepositorie;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
}

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepositorie.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepositorie.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        log.info("Login attempt for username: {}", username);

        if (username == null || password == null) {
            log.error("Username or password is null");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(username);

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", token);
                authData.put("type", "Bearer");

                log.info("Login successful for username: {}", username);
                return ResponseEntity.ok(authData);
            }

            log.warn("Authentication failed for username: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        } catch (AuthenticationException e) {
            log.error("Authentication exception for username {}: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
