package com.rodngo.exercise.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.rodngo.exercise.dto.request.CustomerCreationRequest;
import com.rodngo.exercise.dto.response.CustomerResponse;
import com.rodngo.exercise.entity.Customer;
import com.rodngo.exercise.repository.CustomerRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        customerRepository.delete(customer);
        log.info("Deleted customer with ID: {}", customerId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public CustomerResponse updateCustomer(String customerId, CustomerCreationRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        if (request.getName() != null) {
            customer.setName(request.getName());
        }
        if (request.getPermanentAddress() != null) {
            customer.setPermanentAddress(request.getPermanentAddress());
        }
        if (request.getTemporaryAddress() != null) {
            customer.setTemporaryAddress(request.getTemporaryAddress());
        }
        if (request.getBirthday() != null) {
            customer.setBirthday(request.getBirthday());
        }
        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }
        if (request.getSalary() != null) {
            customer.setSalary(request.getSalary());
        }
        if (request.getEmpNo() != null && !request.getEmpNo().isEmpty()) {
            User user = userRepository.findById(request.getEmpNo())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
            customer.setUser(user);
        }

        customerRepository.save(customer);
        log.info("Updated customer with ID: {}", customerId);

        return CustomerResponse.builder()
                .cif(customer.getCif())
                .name(customer.getName())
                .permanentAddress(customer.getPermanentAddress())
                .temporaryAddress(customer.getTemporaryAddress())
                .birthdayPlace(customer.getBirthdayPlace())
                .birthday(customer.getBirthday())
                .gender(customer.getGender())
                .salary(customer.getSalary())
                .empNo(customer.getUser() != null ?
                        UserResponse.builder().username(customer.getUser().getUsername()).build()
                        : null)
                .build();
    }

    @PreAuthorize("hasAuthority('CREATE_CUSTOMER')")
    public CustomerResponse createCustomer(CustomerCreationRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .permanentAddress(request.getPermanentAddress())
                .temporaryAddress(request.getTemporaryAddress())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .salary(request.getSalary())
                .build();

        UserResponse userResponse = new UserResponse();
        if (request.getEmpNo() != null && !request.getEmpNo().isEmpty()) {
            User user = this.userRepository.findById(request.getEmpNo()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
            customer.setUser(user);
            userResponse = UserResponse.builder()
                    .username(user.getUsername())
                    .build();
        }

        this.customerRepository.save(customer);

        return CustomerResponse.builder()
                .cif(customer.getCif())
                .name(customer.getName())
                .permanentAddress(customer.getPermanentAddress())
                .temporaryAddress(customer.getTemporaryAddress())
                .birthdayPlace(customer.getBirthdayPlace())
                .birthday(customer.getBirthday())
                .gender(customer.getGender())
                .salary(customer.getSalary())
                .empNo(userResponse)
                .build();
    }


    @PreAuthorize("hasRole('ADMIN') or hasAuthority('SHOW_SALARY')")
    public CustomerResponse showSalary(String customerCif) {
        Customer customer = customerRepository.findById(customerCif)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        log.info("Retrieved salary for customer with CIF: {}", customerCif);


        return CustomerResponse.builder()
                .name(customer.getName())
                .salary(customer.getSalary())
                .build();
    }


    public User save(User user) {
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse setPermission(SetPermissionRequest request){
        User user = userService.getById(request.getUserId());
        List<Permission> existingPermissions = user.getPermissions();
        List<Permission> permissionsToAdd = permissionService.getPermissionsByIds(request.getPermissionIds()).stream()
            .filter(permission -> existingPermissions.stream()
            .noneMatch(existing -> existing.getName().equals(permission.getName())))
            .collect(Collectors.toList());
        existingPermissions.addAll(permissionsToAdd);
        user.setPermissions(existingPermissions);
        user = userService.save(user);
        return userService.getUser(user);

    }

    public UserResponse createUser(RegistrationRequest request){
        if (userService.checkExistUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXIST);
        } else {
            User user = create(request);
            return userService.getUser(user);
        }
    }
    
}
