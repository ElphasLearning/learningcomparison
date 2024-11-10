package com.nash.reactive.controller;

import com.nash.reactive.model.CustomerDTO;
import com.nash.reactive.service.BeerService;
import com.nash.reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static  final String CUSTOMER_PATH="/api/v2/customer";

    public static  final String CUSTOMER_PATH_ID = CUSTOMER_PATH+"/{customerId}";

    private final CustomerService customerService;


    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> listCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> listCustomersById(@PathVariable("customerId") Integer customerId){
        return customerService.getCustomerById(customerId);
    }
}
