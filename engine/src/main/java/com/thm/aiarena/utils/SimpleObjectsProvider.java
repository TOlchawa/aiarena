package com.thm.aiarena.utils;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.AObject;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleObjectsProvider {

    private final AArena arena;
    private final Random randomObjectsIndexProvider;
    private List<AObject> aObjectsToProvide;
    private

    @PostConstruct
    void initialize() {
        arena.getAllAObjects();
    }

    public AObject provideAObject() {
        if (aObjectsToProvide == null || aObjectsToProvide.isEmpty()) {
            aObjectsToProvide = new ArrayList<>(arena.getAllAObjects());
        }
        int max = aObjectsToProvide.size();
        int nextIdx = randomObjectsIndexProvider.nextInt(max);
        log.info("randomObjectsIndexProvider: {} ", nextIdx);
        AObject result = aObjectsToProvide.remove(nextIdx);
        if (aObjectsToProvide.isEmpty()) {
            aObjectsToProvide = null;
        }
        return result;
    }

}
