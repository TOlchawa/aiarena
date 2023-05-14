package com.thm.aiarena.config;

import com.thm.aiarena.array.FlatArrayArena;
import com.thm.aiarena.ai.NeuralNetworkSimpleObjectFactory;
import com.thm.aiarena.model.AArena;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.thm.aiarena.SimpleConst.ARENA_SIZE_HEIGHT;
import static com.thm.aiarena.SimpleConst.ARENA_SIZE_WIDTH;

@Configuration
public class ConfigSimpleV1 {


    @Bean
    public AArena getAArena(NeuralNetworkSimpleObjectFactory aObjectFactory) {
        FlatArrayArena arena = FlatArrayArena.builder()
                .width(ARENA_SIZE_WIDTH)
                .height(ARENA_SIZE_HEIGHT)
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
