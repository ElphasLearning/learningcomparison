package com.nash.reactive.services;

import com.nash.reactive.model.CustomerDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(CustomerDTO customerDTO) {
        return null;
    }
}
