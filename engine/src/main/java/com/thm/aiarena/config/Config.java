package com.thm.aiarena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@Configuration
@EnableScheduling
public class Config {
    @Bean
    public Random randomObjectsIndexProvider() {
        return new Random();
    }
}
