package com.nash.reactive.services;

import com.nash.reactive.domain.Beer;
import com.nash.reactive.mappers.BeerMapper;
import com.nash.reactive.model.BeerDTO;
import com.nash.reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @Override
    public Flux<BeerDTO> findByBeerStyle(String beerStyle) {
        return beerRepository.findByBeerStyle(beerStyle)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> findFirstByBeerName(String beerName) {
        return beerRepository.findFirstByBeerName(beerName)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDTO) {
        return beerDTO.map(beerMapper::beerDtoToBeer)
                .flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(BeerDTO beerDTO) {
        return beerRepository.findById(beerDTO.getId()).map(beerMapper::beerToBeerDto);
    }


    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(String beerId) {
        return beerRepository.findById(beerId).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO))
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(foundBeer -> {
                    foundBeer.setBeerName(beerDTO.getBeerName());
                    foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    foundBeer.setPrice(beerDTO.getPrice());
                    foundBeer.setUpc(beerDTO.getUpc());
                    foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(foundBeer -> {
                    if (StringUtils.hasText(beerDTO.getBeerName())) {
                        foundBeer.setBeerName(beerDTO.getBeerName());
                    }
                    if (StringUtils.hasText(beerDTO.getBeerStyle())) {
                        foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    }
                    if (beerDTO.getPrice() != null) {
                        foundBeer.setPrice(beerDTO.getPrice());
                    }
                    if (StringUtils.hasText(beerDTO.getUpc())) {
                        foundBeer.setUpc(beerDTO.getUpc());
                    }
                    if (beerDTO.getQuantityOnHand() != null) {
                        foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    }
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<Void> deleteById(String beerId) {
        return beerRepository.deleteById(beerId);
    }
}

