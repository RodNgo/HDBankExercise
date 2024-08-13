package com.rodngo.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodngo.exercise.entity.Customer;
import com.rodngo.exercise.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    private Customer getCusByCif(String cif){
        return customerRepository.findById(cif).orElse(null);
    }
}
