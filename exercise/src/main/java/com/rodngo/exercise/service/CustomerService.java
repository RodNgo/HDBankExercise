package com.rodngo.exercise.service;

import com.rodngo.exercise.dto.request.CustomerCreationRequest;
import com.rodngo.exercise.entity.User;
import com.rodngo.exercise.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodngo.exercise.dto.response.CustomerResponse;
import com.rodngo.exercise.entity.Customer;
import com.rodngo.exercise.repository.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private ModelMapper modelMapper;

    public Customer createRegularCustomer(CustomerCreationRequest request) {
        Customer customer = new Customer();

        customer.setUser(null);
        customer.setName(request.getName());
        customer.setPermanentAddress(request.getPermanentAddress());
        customer.setTemporaryAddress(request.getTemporaryAddress());
        customer.setBirthday(request.getBirthday());
        customer.setBirthdayPlace(request.getBirthdayPlace());
        customer.setGender(request.getGender());
        customer.setSalary(request.getSalary());

        return customerRepository.save(customer);
    }


    public Customer createCustomer(String empNo, CustomerCreationRequest request) {
        Customer customer = new Customer();

        User user = userRepository.findById(empNo)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + empNo));
        customer.setUser(user);

        customer.setName(request.getName());
        customer.setPermanentAddress(request.getPermanentAddress());
        customer.setTemporaryAddress(request.getTemporaryAddress());
        customer.setBirthday(request.getBirthday());
        customer.setBirthdayPlace(request.getBirthdayPlace());
        customer.setGender(request.getGender());
        customer.setSalary(request.getSalary());

        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

}
