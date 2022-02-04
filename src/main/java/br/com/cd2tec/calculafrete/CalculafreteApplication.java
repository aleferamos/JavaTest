package br.com.cd2tec.calculafrete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CalculafreteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculafreteApplication.class, args);
	}

}
