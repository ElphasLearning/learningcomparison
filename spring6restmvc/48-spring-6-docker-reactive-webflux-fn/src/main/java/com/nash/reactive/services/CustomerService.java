package com.nash.reactive.services;

import com.nash.reactive.model.BeerDTO;
import com.nash.reactive.model.CustomerDTO;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> getCustomerById(CustomerDTO customerDTO);
}
