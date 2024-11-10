package com.nash.reactive.spring_6_reactive_examples.repositories;

import com.nash.reactive.spring_6_reactive_examples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository  {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();

}