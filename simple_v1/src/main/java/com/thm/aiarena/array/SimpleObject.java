package com.thm.aiarena.array;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@With
@Builder
public class SimpleObject implements AObject {

    public SimpleObject() {
    }

    public SimpleObject(AArena arena, ALocation location, Sensor sensor, Motorics motorics, Weapon weapon, Container container, Manipulator manipulator) {
        this.arena = arena;
        this.location = location;
        this.sensor = sensor;
        this.motorics = motorics;
        this.weapon = weapon;
        this.container = container;
        this.manipulator = manipulator;
    }


    @ToString.Exclude
    private AArena arena;

    private ALocation location;

    private Sensor sensor;
    private Motorics motorics;
    private Weapon weapon;
    private Container container;
    private Manipulator manipulator;

    @Override
    public void operate() {
        throw new UnsupportedOperationException();
    }

}
