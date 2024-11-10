package com.nash.webclient.client;

import com.nash.webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void testDelete(){
        AtomicBoolean atomicBoolean =new AtomicBoolean(false);

        beerClient.listBeerDtos()
                .next()
                .flatMap(dto->beerClient.deleteBeer(dto))
                .doOnSuccess(mt->atomicBoolean.set(true))
                .subscribe();
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testPatchBeer(){
        final String NAME = "New Name";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDtos()
                .next()
                .map(beerDTO -> BeerDTO.builder().beerName(NAME).id(beerDTO.getId()).build())
                .flatMap(dto->beerClient.patchBeer(dto))
                .subscribe(byIdDto->{
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testUpdateBeer(){
        final String NAME = "New Name";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDtos()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto->beerClient.updateBeer(dto))
                .subscribe(byIdDto->{
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }


    @Test
    void testCreateBeer(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .quantityOnHand(500)
                .upc("123245")
                .build();

        beerClient.createBeer(newDto)
                .subscribe(dto->{
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerByStyle(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.getBeerByStyle("Pale ale")
                .subscribe(dto->{
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerDtos()
                .flatMap(dto->beerClient.getBeerById(dto.getId()))
                .subscribe(byIdDto->{
                    System.out.println(byIdDto.getBeerName());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }


    @Test
    void listBeers(){
        AtomicBoolean atomicBoolean =new AtomicBoolean(false);
        beerClient.listBeers().subscribe(response->{
            System.out.println(response);
            atomicBoolean.set(true);
        });
      await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerMap(){
        AtomicBoolean atomicBoolean =new AtomicBoolean(false);
        beerClient.listBeersMap().subscribe(response->{
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

   @Test
   void testGetBeerJson(){
       AtomicBoolean atomicBoolean =new AtomicBoolean(false);
       beerClient.listBeersJsonNode().subscribe(jsonNode->{
           System.out.println(jsonNode.toPrettyString());
           atomicBoolean.set(true);
       });
       await().untilTrue(atomicBoolean);
   }

    @Test
    void testGetBeerDto(){
        AtomicBoolean atomicBoolean =new AtomicBoolean(false);
        beerClient.listBeerDtos().subscribe(dto->{
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }



}