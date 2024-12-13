package com.nash.springframework.spring6icecoldservice.services;

import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;

public class DrinkRequestProcessorImpl implements DrinkRequestProcessor {


    @Override
    public void processDrinkRequest(DrinkRequestEvent event) {

        //sleep for 50ms
        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
