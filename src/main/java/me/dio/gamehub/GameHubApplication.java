package me.dio.gamehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@SpringBootApplication
public class GameHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameHubApplication.class, args);
	}

}
