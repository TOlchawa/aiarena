package com.thm.aiarena.model.array;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Data
@Builder
@AllArgsConstructor
public class FlatArrayArena implements AArena {

    private final int width;
    private final int height;
    private final int level;

    private Cell[][] arena;

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
        return arena[x][y].getLocation();
    }

    @Override
    public List<AObject> getObjects(int x, int y, int z) {
        return arena[x][y].getLocation().getObjectSet();
    }

    @Override
    public List<AResource> getResources(int x, int y, int z) {
        return arena[x][y].getLocation().getResourceSet();
    }

    public void init() {
        arena = new Cell[width][height];
        IntStream.range(0, width).forEach(
            x -> IntStream.range(0, height).forEach(
                y -> {
                    arena[x][y] = Cell.builder().location(SimpleLocation.builder()
                            .id(UUID.randomUUID())
                            .objectSet(List.of(SimpleObject.builder().build()))
                            .objectSet(List.of(SimpleResources.builder().build()))
                            .build()).build();
                }
            )
        );
    }

    @Data
    @Builder
    public static class Cell {
        SimpleLocation location;
    }

}
