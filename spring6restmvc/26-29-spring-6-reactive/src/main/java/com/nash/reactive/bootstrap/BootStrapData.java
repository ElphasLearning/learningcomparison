package com.nash.reactive.bootstrap;

import com.nash.reactive.domain.Beer;
import com.nash.reactive.domain.Customer;
import com.nash.reactive.repositories.BeerRepository;
import com.nash.reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private  final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
        beerRepository.count().subscribe(count->{
            System.out.println("Count is :"+count);
        });

        customerRepository.count().subscribe(count->{
            System.out.println("Count is :"+count);
        });

    }

    private void loadCustomerData(){
        Customer customer1 = Customer.builder()
                .customerName("Shiri")
                .build();
        Customer customer2 = Customer.builder()
                .customerName("Nhoro")
                .build();

        Customer customer3 = Customer.builder()
                .customerName("Njiri")
                .build();

        customerRepository.save(customer1).subscribe();
        customerRepository.save(customer2).subscribe();
        customerRepository.save(customer3).subscribe();
    }


    private void loadBeerData() {

            beerRepository.count().subscribe(count->{
                if (count == 0) {
                    Beer beer1 = Beer.builder()
                            .beerName("Galaxy Cat")
                            .beerStyle("Pale ale")
                            .upc("12356")
                            .price(new BigDecimal("12.99"))
                            .quantityOnHand(122)
                            .createdDate(LocalDateTime.now())
                            .lastModifiedDate(LocalDateTime.now())
                            .build();

                    Beer beer2 = Beer.builder()
                            .beerName("Crank")
                            .beerStyle("Pale ale")
                            .upc("12356222")
                            .price(new BigDecimal("11.99"))
                            .quantityOnHand(392)
                            .createdDate(LocalDateTime.now())
                            .lastModifiedDate(LocalDateTime.now())
                            .build();

                    Beer beer3 = Beer.builder()
                            .beerName("Sunshine City")
                            .beerStyle("Pale ale")
                            .upc("12356")
                            .price(new BigDecimal("13.99"))
                            .quantityOnHand(144)
                            .createdDate(LocalDateTime.now())
                            .lastModifiedDate(LocalDateTime.now())
                            .build();

                    beerRepository.save(beer1).subscribe();
                    beerRepository.save(beer2).subscribe();
                    beerRepository.save(beer3).subscribe();
                }
            });
            }


}