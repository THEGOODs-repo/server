package com.umc.thegoods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class thegoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.umc.thegoods.thegoodsApplication.class, args);
    }

}
