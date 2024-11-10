package com.elphas.spring6restmvc;

import com.elphas.spring6restmvc.controllers.BeerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class Spring6RestMvcApplicationTests {
	@Autowired
		BeerController beerController;

	@Test
	void getBeerById(){
		System.out.println(beerController.getBeerById(UUID.randomUUID()));
	}

}
