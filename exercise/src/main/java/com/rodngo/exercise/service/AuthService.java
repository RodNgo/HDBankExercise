package com.rodngo.exercise.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodngo.exercise.dto.request.RegistrationRequest;
import com.rodngo.exercise.dto.request.SetPermissionRequest;
import com.rodngo.exercise.dto.response.UserResponse;
import com.rodngo.exercise.entity.Permission;
import com.rodngo.exercise.entity.User;
import com.rodngo.exercise.exception.AppException;
import com.rodngo.exercise.exception.ErrorCode;
import com.rodngo.exercise.repository.PermissionRepository;
import com.rodngo.exercise.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AuthService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        } else{
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getPermissions().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }


    public User create(RegistrationRequest request) {
        List<Permission> permissions = new ArrayList<>();

        return create(request, permissions);
    }

    public User create(RegistrationRequest request, List<Permission> permissions) {
        User user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).permissions(permissions).build();
        save(user);
        return user;
    }


    public User save(User user) {
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse setPermission(SetPermissionRequest request){
        User user = userService.getById(request.getUserId());
        List<Permission> existingPermissions = user.getPermissions();
        List<Permission> permissionsToAdd = permissionService.getPermissionsByIds(request.getPermissionIds()).stream()
            .filter(permission -> !existingPermissions.contains(permission))
            .collect(Collectors.toList());
        existingPermissions.addAll(permissionsToAdd);
        user.setPermissions(existingPermissions);
        user = userService.save(user);
        return userService.getUser(user);

    }
    
}
