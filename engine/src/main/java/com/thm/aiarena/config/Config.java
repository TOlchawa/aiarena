package com.thm.aiarena.config;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.array.FlatArrayArena;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AArena getAArena() {
        FlatArrayArena arena = FlatArrayArena.builder().width(1000).height(1000).build();
        arena.init();
        return arena;
    }

}
