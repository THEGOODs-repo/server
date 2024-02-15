package com.umc.TheGoods.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {

    private IamportClient iamportClient;

    @Value("${iamport.key}")
    private String restApiKey;

    @Value("${iamport.secret}")
    private String restApiSecret;

    @Bean
    public IamportClient iamportClient(@Value("${iamport.key}") String restApiKey,
                                       @Value("${iamport.secret}") String restApiSecret) {
        return new IamportClient(restApiKey, restApiSecret);
    }
}
