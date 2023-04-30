package com.thm.aiarena.manager;

import com.thm.aiarena.model.AArena;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class GameEngine {

    private final AArena arena;

    @PostConstruct
    void start() {
        IntStream.range(0, arena.getHeight()).forEach(
            x -> IntStream.range(0, arena.getWidth()).forEach(
                y -> {
                    log.info("{},{}", arena.getLocation(x,y,0).getId());
                }
            )
        );
    }

}
