package com.ClaimsManagement.MemberMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableFeignClients
@EnableCaching
public class MemberMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberMicroserviceApplication.class, args);
	}

}
