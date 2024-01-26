package com.umc.TheGoods.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {

    @GetMapping("/test/hello")
    @Operation(summary = "Return hello", description = "simple API for swagger test!")
    public String hello() {
        return "Hello, Swagger!";
    }
}
