package com.generation.stockybackend;

import com.generation.stockybackend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockyBackEndApplicationTests
{

	@Autowired
	UserService serv;

	@Test
	void contextLoads()
	{
//		RegisterDto dto = new RegisterDto();
//		dto.setEmail("admin@mail.it");
//		dto.setPassword("Admin@1234");
//		dto.setName("Gabriel");
//		dto.setSurname("Raffaele");
//		dto.setRole("ADMIN");
//
//		serv.register(dto);
	}

}
