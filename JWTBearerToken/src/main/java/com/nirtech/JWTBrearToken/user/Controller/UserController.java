package com.nirtech.JWTBrearToken.user.Controller;

import com.nirtech.JWTBrearToken.user.dto.UserResponseDTO;
import com.nirtech.JWTBrearToken.user.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.nirtech.JWTBrearToken.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> CreateUser(@RequestBody User user){
        UserResponseDTO result = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String username){
        UserResponseDTO result = userService.findByUserName(username);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public  ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> result = userService.getAllUser();
        return  ResponseEntity.ok(result);
    }

}
