package com.thm.aiarena.utils;

import com.thm.aiarena.ai.NeuralNetworkSimpleObject;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
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
        log.debug("randomObjectsIndexProvider: {} ", nextIdx);
        AObject result = aObjectsToProvide.remove(nextIdx);
        if (aObjectsToProvide.isEmpty()) {
            arena.getAllAObjects().removeIf(o -> o.getContainer().inventory(SimpleResource.TYPE) <= 0);
            aObjectsToProvide = null;
            log.info(".");

            int[] maxValue = new int[] { 0 };
            AObject[] maxObj = new AObject[1];
            arena.getAllAObjects().forEach( obj -> {
                int inventory = obj.getContainer().inventory(SimpleResource.TYPE);
                if (maxValue[0] < inventory) {
                    maxValue[0] = inventory;
                    maxObj[0] = obj;
                }
            });

            ((NeuralNetworkSimpleObject)maxObj[0]).getAi().save("nn.bin");
            ((NeuralNetworkSimpleObject)maxObj[0]).getAi().load("nn.bin");
        }
        return result;
    }

}
