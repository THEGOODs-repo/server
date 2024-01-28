package com.umc.TheGoods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.umc.TheGoods.TheGoodsApplication.class, args);
    }

}
