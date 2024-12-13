package com.nash.springframework.spring6icecoldservice.services;

import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;



public interface DrinkRequestProcessor {

    void processDrinkRequest(DrinkRequestEvent event);
}
