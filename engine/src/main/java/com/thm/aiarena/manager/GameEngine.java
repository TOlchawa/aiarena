package com.thm.aiarena.manager;

import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.Container;
import com.thm.aiarena.utils.SimpleObjectsProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class GameEngine {

    private final AArena arena;
    private final SimpleObjectsProvider aObjectsProvider;

    @Scheduled(fixedDelay = 1000, timeUnit = TimeUnit.MILLISECONDS )
    void process() {
        IntStream.range(0, 1000).forEach( i -> {
            AObject aObj = aObjectsProvider.provideAObject();
            log.debug("aObj: {}", aObj);
            aObj.operate();
        });
    }

    @Scheduled(fixedDelay = 10000, timeUnit = TimeUnit.MILLISECONDS)
    void stats() {
        AtomicInteger totalCount = new AtomicInteger(0);
        AtomicInteger minInventory = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger totalInventory = new AtomicInteger(0);
        AtomicInteger maxInventory = new AtomicInteger(0);
        Map<Integer,AtomicInteger> spicesStats = new HashMap<>();
        arena.getAllAObjects().forEach(obj -> {
            Container container = obj.getContainer();
            int spiece = obj.getTransmitter().response();
            int inventory = container.inventory(SimpleResource.TYPE);
            totalInventory.getAndAdd(inventory);
            totalCount.incrementAndGet();
            minInventory.set(Math.min(minInventory.get(), inventory));
            maxInventory.set(Math.max(maxInventory.get(), inventory));
            if (!spicesStats.containsKey(spiece)) {
                spicesStats.put(spiece, new AtomicInteger());
            }
            spicesStats.get(spiece).incrementAndGet();
        });
        log.info("Total: {} ; Average Inventory: {} ; Min: {}; Max: {}; Spices: {}",
                totalCount.get(), totalInventory.get() / totalCount.intValue(), minInventory.get(), maxInventory.get(), spicesStats);
    }

}
