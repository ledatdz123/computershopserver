package com.example.computershopserver.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class CloudinaryConfig {
    @Value("${clouddinary.cloudName}")
    public String cloudName;
    @Value("${clouddinary.apiKey}")
    private String apiKey;
    @Value("${clouddinary.apiSecret}")
    private String apiSecret;
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("cloud_name", cloudName);
        valueMap.put("api_key", apiKey);
        valueMap.put("api_secret", apiSecret);
        return new Cloudinary(valueMap);
    }
}
