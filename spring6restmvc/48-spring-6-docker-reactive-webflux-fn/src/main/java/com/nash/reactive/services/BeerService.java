package com.nash.reactive.services;

import com.nash.reactive.domain.Beer;
import com.nash.reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> findByBeerStyle(String beerStyle);

    Mono<BeerDTO> findFirstByBeerName(String beerName);

    Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDTO);

    Mono<BeerDTO> getBeerById(BeerDTO beerDTO);

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(String beerId);

    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDTO);

    Mono<Void> deleteById(String beerId);
}
