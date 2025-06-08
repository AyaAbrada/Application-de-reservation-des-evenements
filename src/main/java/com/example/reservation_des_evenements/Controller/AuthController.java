package com.example.reservation_des_evenements.Controller;

import com.example.reservation_des_evenements.Repositories.UserRepositorie;
import com.example.reservation_des_evenements.Services.UserService;
import com.example.reservation_des_evenements.Controller.LoginResponse;
import com.example.reservation_des_evenements.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepositorie userRepositorie;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(UserRepositorie userRepositorie, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService) {
        this.userRepositorie = userRepositorie;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Simule un token, tu peux remplacer ça par une vraie génération JWT
            String fakeToken = "mocked-jwt-token";

            return ResponseEntity.ok(new LoginResponse(fakeToken));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/countUser")
    public int countUser() {
        return userService.allUser();
    }
}
