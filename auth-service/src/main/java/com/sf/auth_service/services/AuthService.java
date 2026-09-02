package com.sf.auth_service.services;


import com.sf.auth_service.entities.UserCredentials;
import com.sf.auth_service.repo.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialsRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String registerUser(UserCredentials credentials) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        repository.save(credentials);
        return "User profile registered successfully!";
    }

    public String loginAndGenerateToken(String username, String password) {
        UserCredentials user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password match parameters."));
        
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(username);
        } else {
            throw new RuntimeException("Security access credentials validation failed.");
        }
    }
}
