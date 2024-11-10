package com.nash.reactive.repositories;

import com.nash.reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer,Integer> {



}
