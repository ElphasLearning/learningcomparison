package com.elphas.spring6restmvc.controllers;

import com.elphas.spring6restmvc.model.Beer;
import com.elphas.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    @Autowired
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){

        return beerService.listBeers();
    }

    @PostMapping
   //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{beerId}",method =RequestMethod.GET )
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer By Id- in service Id :"+beerId.toString());

        return beerService.getBeerById(beerId);
    }
}
