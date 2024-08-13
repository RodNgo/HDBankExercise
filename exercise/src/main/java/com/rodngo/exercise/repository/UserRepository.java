package com.rodngo.exercise.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rodngo.exercise.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
