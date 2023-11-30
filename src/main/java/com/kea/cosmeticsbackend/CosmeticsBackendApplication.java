package com.kea.cosmeticsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CosmeticsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosmeticsBackendApplication.class, args);
    }

}
