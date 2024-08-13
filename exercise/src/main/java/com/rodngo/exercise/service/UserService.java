package com.rodngo.exercise.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodngo.exercise.dto.response.PermissionResponse;
import com.rodngo.exercise.dto.response.UserResponse;
import com.rodngo.exercise.entity.User;
import com.rodngo.exercise.exception.AppException;
import com.rodngo.exercise.exception.ErrorCode;
import com.rodngo.exercise.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserResponse getUser(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .permissions(user.getPermissions().stream()
                        .map(permission -> modelMapper.map(permission, PermissionResponse.class)).toList())
                .build();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getById(String id){
        return userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST));
    }

    public boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
