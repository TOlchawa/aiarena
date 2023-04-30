package com.thm.aiarena.config;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.impl.array.FlatArrayArena;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@Configuration
@EnableScheduling
public class Config {

    @Bean
    public AArena getAArena() {
        FlatArrayArena arena = FlatArrayArena.builder().width(1000).height(1000).build();
        arena.init();
        return arena;
    }

    @Bean
    public Random randomObjectsIndexProvider() {
        return new Random();
    }

}
