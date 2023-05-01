package com.thm.aiarena.array;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@Setter
@ToString
public class SimpleObject implements AObject {

    private UUID id = UUID.randomUUID();

    @ToString.Exclude
    private AArena arena;

    private ALocation location;

    private Sensor sensor;
    private Motorics motorics;
    private Weapon weapon;
    private Container container;
    private Manipulator manipulator;
    private Transmitter transmitter;

    public SimpleObject() {
    }

    /**
     * Required because o error:
     * java: constructor SimpleObject in class com.thm.aiarena.array.SimpleObject cannot be applied to given types;
     *   required: no arguments
     *   found:    com.thm.aiarena.model.AArena,com.thm.aiarena.model.ALocation,com.thm.aiarena.model.aobject.Sensor,com.thm.aiarena.model.aobject.Motorics,com.thm.aiarena.model.aobject.Weapon,com.thm.aiarena.model.aobject.Container,com.thm.aiarena.model.aobject.Manipulator
     *   reason: actual and formal argument lists differ in length
     */
    public SimpleObject(UUID id, AArena arena, ALocation location, Sensor sensor, Motorics motorics, Weapon weapon, Container container, Manipulator manipulator) {
        this.id= id;
        this.arena = arena;
        this.location = location;
        this.sensor = sensor;
        this.motorics = motorics;
        this.weapon = weapon;
        this.container = container;
        this.manipulator = manipulator;
    }



    @Override
    public void operate() {
        throw new UnsupportedOperationException();
    }

}
