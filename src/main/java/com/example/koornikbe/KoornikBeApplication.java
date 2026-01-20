package com.example.koornikbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KoornikBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoornikBeApplication.class, args);
    }

}
