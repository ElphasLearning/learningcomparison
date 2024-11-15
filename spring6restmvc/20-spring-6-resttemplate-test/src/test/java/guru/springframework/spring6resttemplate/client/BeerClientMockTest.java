package guru.springframework.spring6resttemplate.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6resttemplate.config.RestTemplateBuilderConfig;
import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.support.PageableExecutionUtils.getPage;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestTemplateBuilderConfig.class)
public class BeerClientMockTest {
    static final String URL ="http://localhost:8080";


            BeerClient beerClient;


            MockRestServiceServer server;

            @Autowired
            RestTemplateBuilderConfig restTemplateBuilderConfigured;

            @Autowired
            ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = restTemplateBuilderConfigured.build();
    }

    @Test
            void testListBeers() throws JsonProcessingException {
                String payLoad = objectMapper.writeValueAsString(getPage());
                    server.expect(method(HttpMethod.GET))
                            .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
                            .andRespond(withSuccess(payLoad, MediaType.APPLICATION_JSON));

                    Page<BeerDTO> dtos = beerClient.listBeers();
                    assertThat(dtos.getContent().size()).isGreaterThan(0);
            }

        BeerDTO getBeerDto(){
                return BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .price(new BigDecimal("10.99"))
                        .beerName("Mango Bobs")
                        .beerStyle(BeerStyle.IPA)
                        .quantityOnHand(500)
                        .upc("123245")
                        .build();
        }

        BeerDTOPageImpl getPage(){
                return new BeerDTOPageImpl(Arrays.asList(getBeerDto()), 1, 25, 1L);
        }

}
