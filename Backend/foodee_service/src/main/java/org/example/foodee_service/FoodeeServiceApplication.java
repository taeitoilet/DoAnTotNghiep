package org.example.foodee_service;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class FoodeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodeeServiceApplication.class, args);
    }

}
