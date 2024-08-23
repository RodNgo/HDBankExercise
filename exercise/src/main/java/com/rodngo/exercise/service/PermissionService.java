package com.rodngo.exercise.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import com.rodngo.exercise.dto.response.MyPermissionResponse;
import com.rodngo.exercise.dto.response.PermissionResponse;
import com.rodngo.exercise.entity.Permission;
import com.rodngo.exercise.entity.User;
import com.rodngo.exercise.repository.PermissionRepository;
import com.rodngo.exercise.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PermissionService {
    
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Permission save(Permission permission){
        return permissionRepository.save(permission);
    }

    @PreAuthorize("hasAuthority('USER')")
    public MyPermissionResponse UserPermission(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("unauthorized");
        }
        User user = userRepository.findByUsername(authentication.getName());
        if(user!=null){
            return MyPermissionResponse.builder().permissions(getListResponses(user.getPermissions())).build();
        }
        throw new AccessDeniedException("unauthorized");
    }
    @PreAuthorize("hasAuthority('CREATE_TABLE')")
    public MyPermissionResponse Create_TableResponse(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("unauthorized");
        }
        User user = userRepository.findByUsername(authentication.getName());
        if(user!=null){
            return MyPermissionResponse.builder().permissions(getListResponses(user.getPermissions())).build();
        }
        throw new AccessDeniedException("unauthorized");
    }

    public List<Permission> getPermissionsByIds(List<String> permissionIds) {
        return permissionRepository.findAllById(permissionIds);
    }

    public List<PermissionResponse> getListResponses(List<Permission> permissions){
        return permissions.stream().map(permission-> modelMapper.map(permission,PermissionResponse.class)).toList();
    }

    public Permission findPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }

    public Permission createPermission(String name) {
        Permission permission = new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }
    
}
