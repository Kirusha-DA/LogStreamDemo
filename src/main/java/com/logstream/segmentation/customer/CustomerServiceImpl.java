package com.logstream.segmentation.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepo;

    public CustomerServiceImpl(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer create(Customer customer) {
        return customerRepo.save(customer);
    }

    public void deleteById(Long customerId) {
        customerRepo.deleteById(customerId);
    }

    public List<Customer> readAll() {
        return customerRepo.findAll();
    }
    
}
