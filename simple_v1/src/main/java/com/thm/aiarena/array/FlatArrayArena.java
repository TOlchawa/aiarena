package com.thm.aiarena.array;

import com.thm.aiarena.ai.NeuralNetworkSimpleObject;
import com.thm.aiarena.ai.NeuralNetworkSimpleObjectFactory;
import com.thm.aiarena.model.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.thm.aiarena.SimpleConst.SIMPLE_RESOURCE_AMOUNT;

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
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return null;
        }
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
                            .aResources(List.of(SimpleResource.builder().amount(SIMPLE_RESOURCE_AMOUNT).build()))
                            .build()).build();
                }
            )
        );

//        IntStream.range(1, 30).forEach( x -> {
//            IntStream.range(1, 30).forEach( y-> {
//                addAObject(x*10, y*10, aObjectFactory.create((x+y) % 5));
//            });
//        });

        replaceAObject(325, 425, aObjectFactory.create("nn.1.bin", 0));
        replaceAObject(426, 325, aObjectFactory.create("nn.2.bin", 1));
        replaceAObject(326, 326, aObjectFactory.create("nn.3.bin", 2));
        replaceAObject(427, 426, aObjectFactory.create("nn.4.bin", 3));
        replaceAObject(527, 527, aObjectFactory.create("nn.5.bin", 4));
        replaceAObject(227, 227, aObjectFactory.create("nn.6.bin", 5));

    }

    private void addAObject(int x, int y, NeuralNetworkSimpleObject aObject) {
        aObject.setArena(this);
        aObject.setLocation(this.getLocation(x, y, 0));
        allObjects.add(aObject);
        cells[x][y].getLocation().getAObjects().add(aObject);
    }

    private void replaceAObject(int x, int y, NeuralNetworkSimpleObject aObject) {
        List<AObject> aObjectList = cells[x][y].getLocation().getAObjects();
        if (aObjectList != null && !aObjectList.isEmpty()) {
            AObject originalObject = aObjectList.get(0);
            SimpleObject simpleObject = (SimpleObject) originalObject;
            aObjectList.remove(simpleObject);
            allObjects.remove(simpleObject);
            simpleObject.setArena(null);
            simpleObject.setLocation(null);
        }

        addAObject(x,y,aObject);
    }

    @Override
    public void moveAObject(AObject aObject, ALocation targetLocation) {
        SimpleObject simpleObject = (SimpleObject)aObject;
        SimpleLocation fromLocation = (SimpleLocation)simpleObject.getLocation();
        SimpleLocation toLocation = (SimpleLocation)targetLocation;
        if (!((SimpleLocation) targetLocation).getAObjects().isEmpty()) {
            throw new IllegalArgumentException("location cannot contains two object in this implementation!");
        }
        fromLocation.getAObjects().remove(simpleObject);
        toLocation.getAObjects().add(simpleObject);
    }

    @Override
    public void removeAObject(AObject aObject) {
        SimpleLocation simpleLocation = (SimpleLocation)aObject.getLocation();
        int x = simpleLocation.getX();
        int y = simpleLocation.getY();
        cells[x][y].getLocation().getAObjects().remove(aObject);
    }

    @Override
    public void process(AEvent event) {
        switch (event.getType()) {
            case AEvent.NEW_BORN -> {
                SimpleLocation location = (SimpleLocation)event.getLocation();
                SimpleObject source = (SimpleObject)event.getSource();
                cells[location.getX()][location.getY()].getLocation().getAObjects().add(source);
            }
            default -> log.info("Unknown event {}", event);
        }
    }

    @Data
    @Builder
    public static class Cell {
        SimpleLocation location;
    }

}
