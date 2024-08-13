package com.rodngo.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodngo.exercise.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
