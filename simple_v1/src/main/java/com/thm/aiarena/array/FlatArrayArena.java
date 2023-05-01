package com.thm.aiarena.array;

import com.thm.aiarena.*;
import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Slf4j
public class FlatArrayArena implements AArena {

    private final int width;
    private final int height;
    private final int level;

    @ToString.Exclude
    private Cell[][] cells;
    @ToString.Exclude
    private List<AObject> allObjects;
    @ToString.Exclude
    private NeuralNetworkSimpleObjectFactory aObjectFactory;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public ALocation getLocation(int x, int y, int z) {
        return cells[x][y].getLocation();
    }

    @Override
    public List<AObject> getObjects(int x, int y, int z) {
        return cells[x][y].getLocation().getAObjects();
    }

    @Override
    public List<AResource> getResources(int x, int y, int z) {
        return cells[x][y].getLocation().getAResources();
    }

    @Override
    public List<AObject> getAllAObjects() {
        return allObjects;
    }

    public void init() {
        allObjects = new ArrayList<>();
        cells = new Cell[width][height];
        IntStream.range(0, width).forEach(
            x -> IntStream.range(0, height).forEach(
                y -> {
                    cells[x][y] = Cell.builder().location(SimpleLocation.builder()
                            .id(UUID.randomUUID())
                            .x(x)
                            .y(y)
                            .aObjects(new ArrayList<>(1))
                            .aResources(new ArrayList<>(1))
                            .build()).build();
                }
            )
        );
        addAObject(100, 100, aObjectFactory.create());
        addAObject(width-100, height-100, aObjectFactory.create());
    }

    private void addAObject(int x, int y, NeuralNetworkSimpleObject aObject) {
        aObject.withArena(this);
        aObject.withLocation(this.getLocation(x, y, 0));
        allObjects.add(aObject);
        cells[x][y].getLocation().getAObjects().add(aObject);
    }

    @Data
    @Builder
    public static class Cell {
        SimpleLocation location;
    }

}