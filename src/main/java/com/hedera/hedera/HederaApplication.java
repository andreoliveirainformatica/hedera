package com.hedera.hedera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class HederaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HederaApplication.class, args);
	}

}
