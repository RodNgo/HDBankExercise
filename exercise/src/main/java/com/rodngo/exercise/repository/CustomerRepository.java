package com.rodngo.exercise.repository;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rodngo.exercise.entity.Customer;


import java.awt.print.Pageable;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
