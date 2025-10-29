package com.example.NoteIQ_AuthService.Controller;

import com.example.NoteIQ_AuthService.Domain.Users;
import com.example.NoteIQ_AuthService.Exception.InvalidCredentialsException;
import com.example.NoteIQ_AuthService.Exception.UserAlreadyExistsException;
import com.example.NoteIQ_AuthService.Exception.UserNotFoundException;
import com.example.NoteIQ_AuthService.Repository.UserRepository;
import com.example.NoteIQ_AuthService.Security.SecurityTokenGenerator;
import com.example.NoteIQ_AuthService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final SecurityTokenGenerator securityToken;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, SecurityTokenGenerator securityToken, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.securityToken = securityToken;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users users){
        try{
            // Call service — it will throw exception if user already exists
            authService.register(users);
            // Success response
            return new ResponseEntity<>("Registration successful!", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e){
            // Return JSON error response
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage()); // e.getMessage() will now contain your custom message
            return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users){
        try {
            Users user = authService.login(users.getEmailId(), users.getPassword()).get(); // Safe to call get() now
            Map<String, String> tokenMap = securityToken.generateToken(user);
            String fullName = user.getUserName();
            String firstName = fullName.split(" ")[0];
            tokenMap.put("message", "Welcome, " + firstName + "!");
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        } catch (UserNotFoundException | InvalidCredentialsException e){
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("message", "An unexpected error occurred during login.");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-emailId")
    public ResponseEntity<Void> checkEmailExists(@RequestParam String emailId) {
        Optional<Users> user = userRepository.findByEmailId(emailId);
        if (user.isEmpty()) throw new UserNotFoundException("User not found");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, String> data) {
        Users user = userRepository.findByEmailId(data.get("email"))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(data.get("password")));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
