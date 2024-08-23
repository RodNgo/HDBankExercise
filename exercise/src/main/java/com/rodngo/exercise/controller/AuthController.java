package com.rodngo.exercise.controller;

import com.rodngo.exercise.dto.request.CustomerCreationRequest;
import com.rodngo.exercise.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rodngo.exercise.config.JwtTokenUtil;
import com.rodngo.exercise.dto.request.LoginRequest;
import com.rodngo.exercise.dto.request.RegistrationRequest;
import com.rodngo.exercise.dto.request.SetPermissionRequest;
import com.rodngo.exercise.entity.User;
import com.rodngo.exercise.service.AuthService;
import com.rodngo.exercise.service.PermissionService;
import com.rodngo.exercise.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("test")
    public String test(){
        return "Hello, World";
    }

    @PostMapping("login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        String accessToken = jwtTokenUtil.generateAccessToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        LoginResponse res = LoginResponse.builder().id(user.getId())
                                                    .access_token(accessToken)
                                                    .refresh_token(refreshToken)
                                                    .build();
        return ApiResponse.<LoginResponse>builder().result(res).build();
    }

    @PostMapping("my_permission")
    public ApiResponse<MyPermissionResponse> myPermission() {
        return ApiResponse.<MyPermissionResponse>builder()
                .result(permissionService.UserPermission())
                .build();
    }
    
    @PostMapping("set_permission")
    public ApiResponse<UserResponse> setPermission(@RequestBody SetPermissionRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(authService.setPermission(request))
                .build();
    }

    @PostMapping("register")
    public ApiResponse<UserResponse> register(@RequestBody RegistrationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(authService.createUser(request))
                .build();
    }

    @PostMapping("create_customer")
    ApiResponse<CustomerResponse> createCustomer(@RequestBody CustomerCreationRequest request) {
        return ApiResponse.<CustomerResponse>builder()
                .result(this.authService.createCustomer(request))
                .build();
    }

    @GetMapping("/show_salary/{customerCif}")
    public ApiResponse<CustomerResponse> showSalary(@PathVariable String customerCif) {
        CustomerResponse customerResponse = authService.showSalary(customerCif);
        return ApiResponse.<CustomerResponse>builder()
                .result(customerResponse)
                .message("Customer salary retrieved successfully")
                .build();
    }

    @GetMapping("users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("delete_customer/{customerId}")
    public ApiResponse<Void> deleteCustomer(@PathVariable String customerId) {
        authService.deleteCustomer(customerId);
        return ApiResponse.<Void>builder()
                .message("Customer deleted successfully")
                .build();
    }


    @PutMapping("update_customer/{customerId}")
    public ApiResponse<CustomerResponse> updateCustomer(@PathVariable String customerId,
                                                        @RequestBody CustomerCreationRequest request) {
        CustomerResponse updatedCustomer = authService.updateCustomer(customerId, request);
        return ApiResponse.<CustomerResponse>builder()
                .result(updatedCustomer)
                .message("Customer updated successfully")
                .build();
    }
    
}
