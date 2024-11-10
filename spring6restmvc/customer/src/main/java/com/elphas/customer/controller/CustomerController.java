package com.elphas.customer.controller;

import com.elphas.customer.model.Customer;
import com.elphas.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers(){

        return customerService.listCustomers();
    }

    @RequestMapping("/{custId}")
    public Customer getCustomerById(@PathVariable("custId") UUID id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity getsavedCustomer(@RequestBody Customer customer){
        Customer savedCustomercustomer=customerService.saveCustomer(customer);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId")UUID customerId,@RequestBody Customer customer){
            customerService.upadteCustomerById(customerId,customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId")UUID customerId){

        customerService.deleteCustomerById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId")UUID customerId,@RequestBody Customer customer){
        customerService.patchCustomerById(customerId,customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}


