package com.nash.reactive.controller;

import com.nash.reactive.domain.Beer;
import com.nash.reactive.model.BeerDTO;
import com.nash.reactive.repositories.BeerRepository;
import com.nash.reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListBeers() {
        webTestClient.get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void testGetBeerById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void testGetBeerByIdNotFound() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID,99)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void testCreateNewBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    void testCreateNewBeerBadData() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");

            webTestClient.post().uri(BeerController.BEER_PATH)
                    .body(Mono.just(testBeer), BeerDTO.class)
                    .header("Content-type","application/json")
                    .exchange()
                    .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void testUpdateBeer() {
        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateBeerNotFound() {
        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID,99)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void testUpdateBeerBadRequest() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(testBeer),BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void patchExistingBeer() {
        webTestClient.patch()
                .uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testPatchIdNotFound() {
        webTestClient.patch()
                .uri(BeerController.BEER_PATH_ID,99)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(999)
    void deleteById() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    void testDeleteByIdNotFound() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID,99)
                .exchange()
                .expectStatus().isNotFound();

    }
}