package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient{

    private final RestTemplateBuilder restTemplateBuilder;

    //private static final String BASE_URL="http://localhost:8080";
    public static final String GET_BEER_PATH="/api/v1/beer";
    public static final String GET_BEER_BY_ID_PATH="/api/v1/beer/{beerId}";

//    @Override
//    public BeerDTO createBeer(BeerDTO newDto) {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//
//        ResponseEntity<BeerDTO> response = restTemplate.postForEntity(GET_BEER_PATH,newDto,BeerDTO.class);
//        return null;
//    }
@Override
public BeerDTO createBeer(BeerDTO newDto) {
    RestTemplate restTemplate = restTemplateBuilder.build();

    URI uri = restTemplate.postForLocation(GET_BEER_PATH,newDto,BeerDTO.class);
    return restTemplate.getForObject(uri.getPath(),BeerDTO.class);
}

    @Override
    public BeerDTO updateBeer(BeerDTO beerDTO) {
            RestTemplate restTemplate = restTemplateBuilder.build();
            restTemplate.put(GET_BEER_BY_ID_PATH,beerDTO,beerDTO.getId());
        return getBeerById(beerDTO.getId());
    }

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_BEER_BY_ID_PATH,beerId);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
                return restTemplate.getForObject(GET_BEER_BY_ID_PATH,BeerDTO.class,beerId);
    }



    @Override
    public Page<BeerDTO> listBeers() {
        return  this.listBeers(null,null,null,null,null);
    }
    @Override
    public Page<BeerDTO> listBeers(String beerName,String beerStyle, Boolean showInventory, Integer pageNumber,Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();



        //ResponseEntity<String> stringResponse = restTemplate.getForEntity(BASE_URL+GET_BEER_PATH, String.class);
        //ResponseEntity<Map> mapResponse = restTemplate.getForEntity(BASE_URL+GET_BEER_PATH, Map.class);
        //ResponseEntity<JsonNode> jsonResponse = restTemplate.getForEntity(BASE_URL+GET_BEER_PATH, JsonNode.class);
        //System.out.println(stringResponse.getBody());


//        System.out.println("Map Response "+mapResponse.getBody());
//        jsonResponse.getBody().findPath("beer")
//                .elements().forEachRemaining(node->{
//                    System.out.println(node.get("beerName").asText()+" "+node.get("price").asDouble());
//                });
        UriComponentsBuilder uriComponentsBuilder =UriComponentsBuilder.fromPath(GET_BEER_PATH);
        if(beerName!=null){
            uriComponentsBuilder.queryParam("beerName",beerName);
        }
        if(beerName!=null){
            uriComponentsBuilder.queryParam("beerStyle",beerStyle);
        }
        if(beerName!=null){
            uriComponentsBuilder.queryParam("showInventory",showInventory);
        }
        if(beerName!=null){
            uriComponentsBuilder.queryParam("pageNumber",pageNumber);
        }
        if(beerName!=null){
            uriComponentsBuilder.queryParam("pageSize",pageSize);
        }
        ResponseEntity<BeerDTOPageImpl> response = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

        return response.getBody();
    }




}

