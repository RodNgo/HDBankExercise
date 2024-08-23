package com.rodngo.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rodngo.exercise.dto.request.RegistrationRequest;
import com.rodngo.exercise.entity.Permission;
import com.rodngo.exercise.entity.User;
import com.rodngo.exercise.service.AuthService;
import com.rodngo.exercise.service.PermissionService;
import com.rodngo.exercise.service.UserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.config.Configuration;

@SpringBootApplication
@Transactional
@Slf4j
public class ExerciseApplication {
	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
			.setPropertyCondition(Conditions.isNotNull());
		return modelMapper;
	}

	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	
	@Bean
	@Transactional
	CommandLineRunner run(UserService userService,AuthService authService, PermissionService permissionService){
		return args ->{
			List<String> permissionNames = Arrays.asList("USER","ADMIN","SHOW_SALARY","CREATE_CUSTOMER");
			List<Permission> permissions = new ArrayList<>();
			for (String name : permissionNames) {
				// log.info(permissionService.findPermissionByName(name).toString());
				Permission permission = permissionService.findPermissionByName(name);
				if(permission==null){
					permission = permissionService.createPermission(name);
					
				}
				permissions.add(permission);
				
			}
			User user = userService.getByUsername("admin");
			if(user==null){
				authService.create(new RegistrationRequest("admin", "123"), permissions);
			}
			else{
				// log.info(permissions.size().toString());
				
				List<Permission> existingPermissions = user.getPermissions();
				
				List<Permission> permissionsToAdd = permissions.stream()
            .filter(permission -> existingPermissions.stream()
                    .noneMatch(existing -> existing.getName().equals(permission.getName())))
            .collect(Collectors.toList());
				existingPermissions.addAll(permissionsToAdd);
				user.setPermissions(existingPermissions);
				user = userService.save(user);
			}
		};
	}

}
