package com.api.myfavgames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MyFavGamesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyFavGamesApplication.class, args);
	}
}
