package com.rodngo.exercise.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodngo.exercise.dto.response.CustomerResponse;
import com.rodngo.exercise.entity.Customer;
import com.rodngo.exercise.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired 
    private ModelMapper modelMapper;

    
}
