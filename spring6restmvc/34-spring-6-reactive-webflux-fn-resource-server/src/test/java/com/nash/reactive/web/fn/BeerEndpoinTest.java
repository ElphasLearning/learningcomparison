package com.nash.reactive.web.fn;

import com.nash.reactive.domain.Beer;
import com.nash.reactive.mappers.BeerMapperImpl;
import com.nash.reactive.model.BeerDTO;
import com.nash.reactive.services.BeerService;
import com.nash.reactive.services.BeerServiceImplTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;


import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerEndpointTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BeerService beerService;

    @Test
    @Order(1)
     void testListBeersByStyle(){
        final String BEER_STYLE = "TEST";
        BeerDTO testDto = getSavedBeerDto();
        testDto.setBeerStyle(BEER_STYLE);

        //create test data
        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(BeerRouterConfig.BEER_PATH)
                .body(Mono.just(testDto),BeerDTO.class)
                .header("Content-Type","application/json")
                .exchange();

        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(UriComponentsBuilder
                .fromPath(BeerRouterConfig.BEER_PATH)
                .queryParam("beerStyle",BEER_STYLE).build().toUri())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type","application/json")
                .expectBody().jsonPath("$.size()").value(equalTo(1));

    }

    @Test
    @Order(2)
    void testListBeers() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(BeerRouterConfig.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").value(greaterThan(1));
    }

    @Test
    @Order(3)
    void testGetBeerById() {
        BeerDTO testBeer = getSavedBeerDto();
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(BeerRouterConfig.BEER_PATH_ID,testBeer.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void testGetBeerByIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(BeerRouterConfig.BEER_PATH_ID,99)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void testCreateNewBeer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(BeerRouterConfig.BEER_PATH)
                .body(Mono.just(BeerServiceImplTest.getTestBeer()), BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("location");
    }

    @Test
    void testCreateNewBeerBadData() {
        Beer testBeer = BeerServiceImplTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(BeerRouterConfig.BEER_PATH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void testUpdateBeer() {
        BeerDTO beerDTO =getSavedBeerDto();
        beerDTO.setBeerName("New");
        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerRouterConfig.BEER_PATH_ID,beerDTO.getId())
                .body(Mono.just(BeerServiceImplTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateBeerNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerRouterConfig.BEER_PATH_ID,99)
                .body(Mono.just(BeerServiceImplTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testUpdateBeerBadRequest() {
        Beer testBeer = BeerServiceImplTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerRouterConfig.BEER_PATH_ID,1)
                .body(Mono.just(testBeer),BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void patchExistingBeer() {
        BeerDTO beerDTO =getSavedBeerDto();
        webTestClient.mutateWith(mockOAuth2Login())
                .patch()
                .uri(BeerRouterConfig.BEER_PATH_ID,beerDTO.getId())
                .body(Mono.just(BeerServiceImplTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testPatchIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .patch()
                .uri(BeerRouterConfig.BEER_PATH_ID,99)
                .body(Mono.just(BeerServiceImplTest.getTestBeer()),BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(999)
    void deleteById() {
        BeerDTO beerDTO =getSavedBeerDto();
        webTestClient.mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerRouterConfig.BEER_PATH_ID,beerDTO.getId())
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    void testDeleteByIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerRouterConfig.BEER_PATH_ID,99)
                .exchange()
                .expectStatus().isNotFound();

    }

    public BeerDTO getSavedBeerDto(){
        return beerService.saveBeer(Mono.just(getTestBeerDto())).block();
    }

    private static BeerDTO getTestBeerDto() {
        return new BeerMapperImpl().beerToBeerDto(getTestBeer());
    }


    public static Beer getTestBeer(){
        return Beer.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123213")
                .build();
    }
}