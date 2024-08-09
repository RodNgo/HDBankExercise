package com.rodngo.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public UserEntity login(@RequestParam String username, @RequestParam String password){
        UserEntity userEntity = userService.getUserByName(username);
        if (userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())){
            return userEntity;
        }
        return null;
    }
    @PostMapping("/register")
    public UserEntity Register (@RequestBody UserEntity userEntity){
        return UserService.registerUser(userEntity);
    }
}
