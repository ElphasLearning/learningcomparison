package com.elphas.customer.service;

import com.elphas.customer.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    void upadteCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);



    void patchCustomerById(UUID customerId, Customer customer);
}
