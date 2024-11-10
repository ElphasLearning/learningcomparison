package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;


    @Test
    void testDeleteBeer(){
        BeerDTO newDTO= BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();
        BeerDTO beerDto = beerClient.createBeer(newDTO);
        beerClient.deleteBeer(beerDto.getId());
        assertThrows(HttpClientErrorException.class,()->{
            beerClient.getBeerById(beerDto.getId());
        });
    }

    @Test
    void testupdateBeer(){
        BeerDTO newDTO= BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs 2")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();
        BeerDTO beerDTO = beerClient.createBeer(newDTO);

        final String newName = "Mango Bobs 3";
        beerDTO.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDTO);
        assertEquals(newName,updatedBeer.getBeerName());
    }
    @Test
    void testCreateBeer(){
        BeerDTO newDTO= BeerDTO.builder()
                                .price(new BigDecimal("10.99"))
                                .beerName("Mango Bobs")
                                .beerStyle(BeerStyle.IPA)
                                .quantityOnHand(500)
                                .upc("123245")
                                .build();
            BeerDTO savedDTO = beerClient.createBeer(newDTO);
            assertNotNull(savedDTO);
    }

    @Test
    void getBeerById(){
        Page<BeerDTO> beerDTOS =beerClient.listBeers();
        BeerDTO dto= beerDTOS.getContent().get(0);
        BeerDTO byId = beerClient.getBeerById(dto.getId());
        assertNotNull(byId);
    }
    @Test
    void listBeers() {
        beerClient.listBeers("ALE",null,null,null,null);
    }

    @Test
    void listBeersNoName() {
        beerClient.listBeers(null,null,null,null,null);
    }


}