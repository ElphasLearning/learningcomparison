package com.nash.springframework.spring6icecoldservice.listeners;

import com.nash.springframework.spring6icecoldservice.config.KafkaConfig;
import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import guru.springframework.spring6restmvcapi.model.BeerDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderLineDTO;
import guru.springframework.spring6restmvcapi.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true, topics = {KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC,
                                                        KafkaConfig.DRINK_PREPARED_TOPIC},partitions = 1)
class DrinkRequestListenerTest {

    @Autowired
    DrinkRequestListener drinkRequestListener;

    @Autowired
    DrinkPreparedListener drinkPreparedListener;

    @Test
    void listenDrinkRequest(){
        drinkRequestListener.listenDrinkRequest(DrinkRequestEvent.builder()
                        .beerOrderLineDTO(createDTO())
                .build());

        await().atLeast(5, TimeUnit.SECONDS).untilAsserted(()->{
            assertEquals(1,drinkPreparedListener.iceColdMessageCounter.get());
        });

    }

    public BeerOrderLineDTO createDTO(){
        BeerOrderLineDTO.builder()
                .beer(BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.IPA)
                        .beerName("Test Beer")
                        .build())
                .build();
    }

}