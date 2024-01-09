package com.logstream.segmentation.customer;

import java.util.List;

public interface CustomerService {

    Customer create(Customer customer);
    
    List<Customer> readAll();
    
    void deleteById(Long customerId);
} 