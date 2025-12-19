package com.nirtech.JWTBrearToken.user.config;

import com.nirtech.JWTBrearToken.user.Repository.UserRepository;
import com.nirtech.JWTBrearToken.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor  //this creates the constructor for final fields
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // 1. UserDetailsService: Tells Spring how to load a user entity from the DB
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    // 2. AuthenticationProvider: The service that authenticates and checks the password
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Uses the method above
        authProvider.setPasswordEncoder(passwordEncoder); // Uses the BCrypt encoder
        return authProvider;
    }

    // 3. AuthenticationManager: The main engine for processing login requests
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
