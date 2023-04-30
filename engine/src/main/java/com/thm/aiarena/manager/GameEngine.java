package com.thm.aiarena.manager;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.utils.SimpleObjectsProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class GameEngine {

    private final AArena arena;
    private final SimpleObjectsProvider aObjectsProvider;

    @Scheduled(fixedDelay = 1000, timeUnit = TimeUnit.MILLISECONDS)
    void process() {
//        log.info("process: {}", arena);
//        arena.getAllAObjects().stream().forEach(
//            aObj -> {
//                log.info("aObj: {}", aObj);
//            }
//        );
        AObject aObj = aObjectsProvider.provideAObject();
        log.info("aObj: {}", aObj);

        aObj.process();

    }

}
