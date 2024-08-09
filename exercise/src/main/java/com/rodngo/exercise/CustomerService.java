package com.rodngo.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    private Customer getCusByCif(Long cif){
        return customerRepository.findById(cif).orElse(null);
    }
}
