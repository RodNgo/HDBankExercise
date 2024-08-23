package com.rodngo.exercise.controller;


import com.rodngo.exercise.dto.request.CustomerCreationRequest;
import com.rodngo.exercise.dto.response.ApiResponse;
import com.rodngo.exercise.dto.response.CustomerResponse;
import com.rodngo.exercise.entity.Customer;
import com.rodngo.exercise.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    ApiResponse<List<Customer>> getALl() {
        return ApiResponse.<List<Customer>>builder()
                .result(this.customerService.getCustomers())
                .build();
    }


}
