package com.sf.auth_service.controllers;


import com.sf.auth_service.entities.UserCredentials;
import com.sf.auth_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCredentials user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody UserCredentials user) {
        return ResponseEntity.ok(authService.loginAndGenerateToken(user.getUsername(), user.getPassword()));
    }

	
}
