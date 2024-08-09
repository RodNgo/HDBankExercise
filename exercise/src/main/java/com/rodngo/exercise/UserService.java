package com.rodngo.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userResporitory;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static UserEntity registerUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userResporitory.save(userEntity);
    }
    public UserEntity getUserByName(String username ){
        return userResporitory.findByUsername(username);
    }

}
