package com.logstream.segmentation.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logstream.segmentation.segment.Segment;

@RestController
@RequestMapping(path="/api/customers",
                produces="application/json")
@CrossOrigin(origins="http://segmentclound:8080")
public class CustomerController {

    private CustomerService customerSrvc;

    @Autowired
    public void SegmentController(CustomerService customerSrvc) {
        this.customerSrvc = customerSrvc;
    }

    @GetMapping
    @JsonIgnoreProperties(value="segments")
    public ResponseEntity<List<Customer>> readAll() {
        List<Customer> customers = customerSrvc.readAll();
        if (customers != null) {
            return ResponseEntity.ok(customers);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer newCustomer = customerSrvc.create(customer);
        String newURI = String.format(
            "http://localhost:%d/api/segments/%d",
            8080, newCustomer.getId()
        );

        return ResponseEntity.created(
            UriComponentsBuilder
            .fromUriString(newURI)
            .build()
            .toUri()
        ).build();

    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("customerId") Long customerId) {
        try {
            customerSrvc.deleteById(customerId);
        } catch (EmptyResultDataAccessException e) {}
    }
    
}
