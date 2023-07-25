package com.thm.aiarena.manager;

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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class GameEngine {

    private final AArena arena;
    private final SimpleObjectsProvider aObjectsProvider;

    @Scheduled(fixedDelay = 100, timeUnit = TimeUnit.MILLISECONDS)
    void process() {
        IntStream.range(0, 1000).forEach( i -> {
            AObject aObj = aObjectsProvider.provideAObject();
            log.debug("aObj: {}", aObj);
            if (aObj != null) {
                aObj.operate();
            } else {
                System.exit(1);
            }
        });
    }

    @Scheduled(fixedDelay = 10000, timeUnit = TimeUnit.MILLISECONDS)
    void stats() {
        AtomicInteger totalCount = new AtomicInteger(0);
        AtomicInteger minInventory = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger totalInventory = new AtomicInteger(0);
        AtomicInteger maxInventory = new AtomicInteger(0);
        Map<Integer,AtomicInteger> spicesStats = new HashMap<>();
        Map<Integer,AtomicLong> spicesValueStats = new HashMap<>();
        Map<Integer,AtomicLong> spicesMaxValueStats = new HashMap<>();
        arena.getAllAObjects().forEach(obj -> {
            Container container = obj.getContainer();
            int spiece = obj.getTransmitter().response();
            int inventory = container.inventory(1); // this is: SimpleResource.TYPE
            totalInventory.getAndAdd(inventory);
            totalCount.incrementAndGet();
            minInventory.set(Math.min(minInventory.get(), inventory));
            maxInventory.set(Math.max(maxInventory.get(), inventory));
            if (!spicesStats.containsKey(spiece)) {
                spicesStats.put(spiece, new AtomicInteger(0));
                spicesValueStats.put(spiece, new AtomicLong(0));
                spicesMaxValueStats.put(spiece, new AtomicLong(0));
            }
            spicesStats.get(spiece).incrementAndGet();
            spicesValueStats.get(spiece).addAndGet(inventory);
            spicesMaxValueStats.get(spiece).set(Math.max(inventory, spicesMaxValueStats.get(spiece).get()));
        });
        log.info("Total: {} ; Average Inventory: {} ; Min: {}; Max: {}",
                totalCount.get(), totalInventory.get() / totalCount.intValue(), minInventory.get(), maxInventory.get());
        spicesStats.keySet().forEach(
            k -> {
                log.info("spices: {}; count: {}; avgValue: {}; max inventory: {}",
                        k, spicesStats.get(k), spicesValueStats.get(k).get() / spicesStats.get(k).get(), spicesMaxValueStats.get(k).get());
            }
        );
    }

}
