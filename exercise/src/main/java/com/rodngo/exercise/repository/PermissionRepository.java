package com.rodngo.exercise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodngo.exercise.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
    Permission findByName(String name);

    List<Permission> findAllByNameIn(List<String> names);

    
}
