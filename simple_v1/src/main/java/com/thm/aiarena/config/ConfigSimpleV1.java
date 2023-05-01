package com.thm.aiarena.config;

import com.thm.aiarena.array.FlatArrayArena;
import com.thm.aiarena.array.NeuralNetworkSimpleObjectFactory;
import com.thm.aiarena.model.AArena;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSimpleV1 {

    @Bean
    public AArena getAArena(NeuralNetworkSimpleObjectFactory aObjectFactory) {
        FlatArrayArena arena = FlatArrayArena.builder()
                .width(1000)
                .height(1000)
                .aObjectFactory(aObjectFactory)
                .build();
        arena.init();
        return arena;
    }

    @Bean
    public NeuralNetworkSimpleObjectFactory getAObjectFactory() {
        return new NeuralNetworkSimpleObjectFactory();
    }

}
