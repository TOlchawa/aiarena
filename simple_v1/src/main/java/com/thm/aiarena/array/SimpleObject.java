package com.thm.aiarena.array;

import com.thm.aiarena.SimpleMemory;
import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Getter
@Setter
@ToString
public class SimpleObject implements AObject {

    private UUID id = UUID.randomUUID();
    private UUID parentId = null;

    @ToString.Exclude
    private AArena arena;

    private ALocation location;

    private Sensor sensor;
    private Locomotion locomotion;
    private Weapon weapon;
    private Container container;
    private Manipulator manipulator;
    private Transmitter transmitter;
    private Creator creator;
    private Memory memory;
    private int species;
    private int fiends;
    private int enemies;

    public SimpleObject() {
    }

    /**
     * Required because o error:
     * java: constructor SimpleObject in class com.thm.aiarena.array.SimpleObject cannot be applied to given types;
     *   required: no arguments
     *   found:    com.thm.aiarena.model.AArena,com.thm.aiarena.model.ALocation,com.thm.aiarena.model.aobject.Sensor,com.thm.aiarena.model.aobject.Motorics,com.thm.aiarena.model.aobject.Weapon,com.thm.aiarena.model.aobject.Container,com.thm.aiarena.model.aobject.Manipulator
     *   reason: actual and formal argument lists differ in length
     */
    public SimpleObject(UUID id, int species, AArena arena, ALocation location, Sensor sensor, Locomotion locomotion, Weapon weapon, Container container, Manipulator manipulator) {
        this.id= id;
        this.species = species;
        this.arena = arena;
        this.location = location;
        this.sensor = sensor;
        this.locomotion = locomotion;
        this.weapon = weapon;
        this.container = container;
        this.manipulator = manipulator;
        this.fiends = 0;
        this.enemies = 0;
    }



    @Override
    public void operate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAlive() {
        return getContainer() != null && getContainer().inventory(SimpleResource.TYPE) > 0;
    }

    public void updateVision() {
        int[] result = {0, 0};

        IntStream.range(-1,1).forEach(
            dx -> IntStream.range(-1, 1).forEach(
                dy -> {
                    if (dx != 0 || dy != 0) {
                        int[] scanResult = scan(dx, dy);
                        result[0] |= scanResult[0];
                        result[1] |= scanResult[1];
                    }
                }
            )
        );

        fiends = result[0];
        enemies = result[1];

    }

    private int[] scan(int dx, int dy) {
        int[] response = { 0, 0 };

        SimpleLocation simpleLocation = ((SimpleLocation) getLocation());
        int x = simpleLocation.getX();
        int y = simpleLocation.getY();
        SimpleLocation scannedLocation = (SimpleLocation)getArena().getLocation(x + dx, y + dy, 0);

        if (!scannedLocation.getAObjects().isEmpty()) {
            SimpleObject scannedObject = (SimpleObject) scannedLocation.getAObjects().get(0);
            if (scannedObject.getTransmitter().response() == this.getTransmitter().response()) {
                response[0] += markPosition(dx, dy); // friend
            } else {
                response[1] += markPosition(dx, dy); // enemy
            }
        }

        return response;
    }


    public int markPosition(int x, int y) {
        int resultX = switch (x) {
            case -1 -> 0b0001;
            case 1 ->  0b0100;
            default -> 0b0000;
        };
        int resultY = switch (y) {
            case -1 -> 0b0010;
            case 1 ->  0b1000;
            default -> 0b0000;
        };
        return resultX | resultY;
    }

    public int getSpecie() {
        return species;
    }

}
