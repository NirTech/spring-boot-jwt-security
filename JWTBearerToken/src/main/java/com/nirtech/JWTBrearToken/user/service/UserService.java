package com.nirtech.JWTBrearToken.user.service;

import com.nirtech.JWTBrearToken.user.dto.UserResponseDTO;
import com.nirtech.JWTBrearToken.user.entity.User;
import com.nirtech.JWTBrearToken.user.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nirtech.JWTBrearToken.user.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelmapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUser(){
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> modelmapper.map(user,UserResponseDTO.class)
                        )
                .collect(Collectors.toList());
    }

    public  UserResponseDTO findByUserName(String userName){
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        UserResponseDTO result = modelmapper.map(user, UserResponseDTO.class);
        return result;
    }

    public UserResponseDTO saveUser(User user){

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User result = userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(result.getId(), result.getUsername());
        return userResponseDTO;
    }
}
