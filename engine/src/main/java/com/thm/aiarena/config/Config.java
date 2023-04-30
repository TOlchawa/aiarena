package com.thm.aiarena.config;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.array.FlatArrayArena;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class Config {

    @Bean
    public AArena getAArena() {
        FlatArrayArena arena = FlatArrayArena.builder().width(1000).height(1000).build();
        arena.init();
        return arena;
    }

}
