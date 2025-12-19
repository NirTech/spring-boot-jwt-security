package com.nirtech.JWTBrearToken.user.Controller;

import com.nirtech.JWTBrearToken.user.dto.AuthRequestDTO;
import com.nirtech.JWTBrearToken.user.dto.AuthResponseDTO;
import com.nirtech.JWTBrearToken.user.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthRequestDTO authRequestDTO){
        //1. HHands credentials to the Authentication Manager
        // This triggers the whole chain: Provider -> UserDetailsService -> PasswordEncoder
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()
                )
        );
        // 2. If we reach this line, it means authentication was successful!
        // If it failed, authenticationManager would have thrown an Exception.
        if (authentication.isAuthenticated()) {
            // 3. Generate the token for the user
            String token = jwtService.generateToken(authRequestDTO.getUsername());

            // 4. Return the token wrapped in our Response DTO
            return ResponseEntity.ok(new AuthResponseDTO(token));
        }
        else{
            throw new RuntimeException("Invalid username or password.");
        }
    }
}
