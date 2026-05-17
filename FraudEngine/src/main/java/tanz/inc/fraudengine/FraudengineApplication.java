package tanz.inc.fraudengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class FraudengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudengineApplication.class, args);
	}

}
